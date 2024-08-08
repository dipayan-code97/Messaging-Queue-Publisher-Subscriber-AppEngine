package publisherSubscriberQueue.backend.abstractorInterfaceChannel;

import lombok.NonNull;
import publisherSubscriberQueue.backend.handler.TopicHandler;
import publisherSubscriberQueue.backend.model.Message;
import publisherSubscriberQueue.backend.model.Topic;
import publisherSubscriberQueue.backend.model.TopicSubscriber;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class PublisherSubscriberQueue {
    private final Map<String, TopicHandler> TOPIC_PROCESSOR;

    public PublisherSubscriberQueue() {
        this.TOPIC_PROCESSOR = new LinkedHashMap<>();
    }

    public Map<String, TopicHandler> getTOPIC_PROCESSOR() {
        return this.TOPIC_PROCESSOR;
    }

    public Topic createTopic(@NonNull final String topicName) {

        final Topic topic = new Topic(topicName, UUID.randomUUID().toString());
        TopicHandler topicHandler = new TopicHandler(topic);
        TOPIC_PROCESSOR.put(topic.getTopicSerialCode(), topicHandler);
        System.out.println("Created topic: " + topic.getTopicName());
        return topic;
    }

    public void subscribe(@NonNull final ISubscriber subscriber,
                          @NonNull final Topic topic) {
        topic.addSubscriber(new TopicSubscriber(subscriber));
        System.out.println(subscriber.getSubscriberSerialCode() + " subscribed to topic: " + topic.getTopicName());
    }

    public void publish(@NonNull final Topic topic,
                        @NonNull final Message message) {
        topic.addMessage(message);
        System.out.println(message.getTextMessage() + " published to topic: " + topic.getTopicName());
        new Thread(() -> TOPIC_PROCESSOR.get(topic.getTopicSerialCode()).publish()).start();
    }

    public void resetOffset(@NonNull final Topic topic, @NonNull final ISubscriber subscriber,
                            @NonNull final Integer newOffset) {
        for (TopicSubscriber topicSubscriber : topic.getTopicSubscriber()) {
            if (topicSubscriber.getSubscriber().equals(subscriber)) {
                topicSubscriber.getOffset().set(newOffset);
                System.out.println(topicSubscriber.getSubscriber().getSubscriberSerialCode() + " offset reset to: " + newOffset);
                new Thread(() -> TOPIC_PROCESSOR.get(topic.getTopicSerialCode()).startSubscribedWorker(topicSubscriber)).start();
                break;
            }
        }
    }

    @Override
    public boolean equals(Object objRef) {
        if (this == objRef) return true;
        if (!(objRef instanceof PublisherSubscriberQueue)) return false;
        PublisherSubscriberQueue that = (PublisherSubscriberQueue) objRef;
        return (Objects.equals(getTOPIC_PROCESSOR(), that.getTOPIC_PROCESSOR()));
    }

    @Override
    public int hashCode() {
        return (Objects.hash(getTOPIC_PROCESSOR()));
    }

    @Override
    public String toString() {
        return ("PublisherSubscriberQueue{" +
                "TOPIC_PROCESSOR=" + TOPIC_PROCESSOR +
                '}');
    }
}
