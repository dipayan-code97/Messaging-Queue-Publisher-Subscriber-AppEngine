package publisherSubscriberQueue.backend.queueAlgorithm;

import lombok.NonNull;
import publisherSubscriberQueue.backend.abstractorInterfaceChannel.ISubscriber;
import publisherSubscriberQueue.backend.handler.TopicHandler;
import publisherSubscriberQueue.backend.model.Message;
import publisherSubscriberQueue.backend.model.Topic;
import publisherSubscriberQueue.backend.model.TopicSubscriber;

import java.sql.Time;
import java.util.*;

public class SleepingSubscriber implements ISubscriber {
    private final Map<String, TopicHandler> SUBSCRIBER_BUCKET;
    private final String SUBSCRIBER_SERIAL_CODE;
    private final Time SLEEP_TIME_IN_MILLIS;

    public SleepingSubscriber(String subscriberSerialCode, Time sleepTimeInMillis) {
        this.SUBSCRIBER_SERIAL_CODE = subscriberSerialCode;
        this.SLEEP_TIME_IN_MILLIS = sleepTimeInMillis;
        this.SUBSCRIBER_BUCKET = new LinkedHashMap<>();
    }

    public String getSubscriberSerialCode() {
        return this.SUBSCRIBER_SERIAL_CODE;
    }

    public Time getSleepTimeInMillis() {
        return this.SLEEP_TIME_IN_MILLIS;
    }

    public Map<String, TopicHandler> getTopicProcessor() {
        return this.SUBSCRIBER_BUCKET;
    }

    @Override
    public void consume(@NonNull final Message textMessage) throws InterruptedException {
        System.out.println("Subscriber: " + SUBSCRIBER_SERIAL_CODE + " started consuming: " + textMessage.getTextMessage());
        Thread.sleep(SLEEP_TIME_IN_MILLIS.getTime());
        System.out.println("Subscriber: " + SUBSCRIBER_SERIAL_CODE + " done consuming: " + textMessage.getTextMessage());
    }

    @Override
    public Topic createTopic(@NonNull final String topicName) {
        final Topic topic = new Topic(topicName, UUID.randomUUID().toString());
        TopicHandler topicHandler = new TopicHandler(topic);
        SUBSCRIBER_BUCKET.put(topic.getTopicSerialCode(), topicHandler);
        System.out.println("Created topic: " + topic.getTopicName());
        return topic;
    }

    @Override
    public void subscribe(@NonNull ISubscriber subscriber, @NonNull Topic topic) {
        topic.addSubscriber(new TopicSubscriber(subscriber));
        System.out.println(subscriber.getSubscriberSerialCode() + " subscribed to topic: " + topic.getTopicName());
    }

    @Override
    public void publish(@NonNull final Topic topic, @NonNull final Message message) {
        topic.addMessage(message);
        System.out.println(message.getTextMessage() + " published to topic: " + topic.getTopicName());
        new Thread(() -> SUBSCRIBER_BUCKET.get(topic.getTopicSerialCode()).publish()).start();
    }

    @Override
    public void resetOffset(@NonNull final Topic topic, @NonNull ISubscriber subscriber,
                            @NonNull final Integer newOffset) {
        for (TopicSubscriber topicSubscriber : topic.getTopicSubscriber()) {
            if (topicSubscriber.getSubscriber().equals(subscriber)) {
                topicSubscriber.getOffset().set(newOffset);
                System.out.println(topicSubscriber.getSubscriber().getSubscriberSerialCode() + " offset reset to: " + newOffset);
                new Thread(() -> SUBSCRIBER_BUCKET.get(topic.getTopicSerialCode()).startSubscribedWorker(topicSubscriber)).start();
                break;
            }
        }
    }

    @Override
    public boolean equals(Object objRef) {
        if (this == objRef) return true;
        if (!(objRef instanceof SleepingSubscriber)) return false;
        SleepingSubscriber that = (SleepingSubscriber) objRef;
        return (Objects.equals(SUBSCRIBER_SERIAL_CODE, that.SUBSCRIBER_SERIAL_CODE)
                && Objects.equals(SLEEP_TIME_IN_MILLIS, that.SLEEP_TIME_IN_MILLIS));
    }

    @Override
    public int hashCode() {
        return (Objects.hash(SUBSCRIBER_SERIAL_CODE,
                SLEEP_TIME_IN_MILLIS));
    }

    @Override
    public String toString() {
        return ("SleepingSubscriber{" +
                "SUBSCRIBER_SERIAL_CODE='" + SUBSCRIBER_SERIAL_CODE + '\'' +
                ", SLEEP_TIME_IN_MILLIS=" + SLEEP_TIME_IN_MILLIS +
                '}');
    }
}
