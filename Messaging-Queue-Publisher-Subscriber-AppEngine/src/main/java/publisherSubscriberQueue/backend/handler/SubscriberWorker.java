package publisherSubscriberQueue.backend.handler;

import lombok.NonNull;
import lombok.SneakyThrows;
import publisherSubscriberQueue.backend.model.Message;
import publisherSubscriberQueue.backend.model.Topic;
import publisherSubscriberQueue.backend.model.TopicSubscriber;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static java.awt.desktop.UserSessionEvent.Reason.LOCK;

public class SubscriberWorker implements Runnable {

    private final Topic TOPIC;
    private final TopicSubscriber TOPIC_SUBSCRIBER;
    private static final AtomicInteger OFFSET = new AtomicInteger();
    private static final AtomicInteger SOME_MAXIMUM_OFFSET_VALUE = new AtomicInteger();
    public SubscriberWorker(@NonNull final Topic topic,
                            @NonNull final TopicSubscriber topicSubscriber,
                            @NonNull final AtomicInteger OFFSET) {
        this.TOPIC = topic;
        this.TOPIC_SUBSCRIBER = topicSubscriber;
    }

    public SubscriberWorker(@NonNull final Topic topic,
                            @NonNull final TopicSubscriber topicSubscriber) {
        this.TOPIC = topic;
        this.TOPIC_SUBSCRIBER = topicSubscriber;
    }

    public Topic getTopic() {
        return this.TOPIC;
    }

    public TopicSubscriber getTopicSubscriber() {
        return this.TOPIC_SUBSCRIBER;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (LOCK) {
            boolean breakCondition = OFFSET.get() < OFFSET.addAndGet(Integer.parseInt(String.valueOf(SOME_MAXIMUM_OFFSET_VALUE)));
            do {
                int currentOffset = OFFSET.get();

                while (currentOffset >= TOPIC.getTextMessage().size()) {
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                Message message = new Message("message-1");
                TOPIC.getTextMessage().contains(currentOffset);
                TOPIC.getTopicSubscriber().contains(message);

                // Increment the offset after consuming the message
                OFFSET.incrementAndGet();
            } while (breakCondition); // You need a condition to break the loop
        }
    }
    synchronized public void wakeUpIfNeeded() {
        synchronized (TOPIC_SUBSCRIBER) {
            TOPIC_SUBSCRIBER.notify();
        }
    }

    @Override
    public int hashCode() {
        return (Objects.hash(getTopic(), getTopicSubscriber()));
    }

    @Override
    public String toString() {
        return ("SubscriberWorker{" +
                "Topic=" + TOPIC +
                ", TopicSubscriber=" + TOPIC_SUBSCRIBER +
                '}');
    }
}
