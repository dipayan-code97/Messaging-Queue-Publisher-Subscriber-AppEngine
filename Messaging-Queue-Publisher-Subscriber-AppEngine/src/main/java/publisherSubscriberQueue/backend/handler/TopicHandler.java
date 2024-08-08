package publisherSubscriberQueue.backend.handler;

import publisherSubscriberQueue.backend.model.Topic;
import publisherSubscriberQueue.backend.model.TopicSubscriber;
import lombok.NonNull;

import java.util.*;

public class TopicHandler {
    private final Topic TOPIC;
    private final Map<String, SubscriberWorker> TOPIC_HANDLER;


    public TopicHandler(@NonNull final Topic TOPIC) {
        this.TOPIC = TOPIC;
        TOPIC_HANDLER = new LinkedHashMap<>();
    }

    public Topic getTopic() {
        return this.TOPIC;
    }

    public Map<String, SubscriberWorker> getSubscriberWorkers() {
        return this.TOPIC_HANDLER;
    }

    public void publish() {
        for (TopicSubscriber topicSubscriber: TOPIC.getTopicSubscriber()) {
            startSubscribedWorker(topicSubscriber);
        }
    }

    public void startSubscribedWorker(@NonNull final TopicSubscriber topicSubscriber) {
        final String subscriberId = topicSubscriber.getSubscriber().getSubscriberSerialCode();
        if (!TOPIC_HANDLER.containsKey(subscriberId)) {
            final SubscriberWorker subscriberWorker = new SubscriberWorker(TOPIC, topicSubscriber);
            TOPIC_HANDLER.put(subscriberId, subscriberWorker);
            new Thread(subscriberWorker).start();
        }
        final SubscriberWorker subscriberWorker = TOPIC_HANDLER.get(subscriberId);
        subscriberWorker.wakeUpIfNeeded();
    }

    @Override
    public boolean equals(Object objRef) {
        if (this == objRef) return true;
        if (!(objRef instanceof TopicHandler)) return false;
        TopicHandler that = (TopicHandler) objRef;
        return (Objects.equals(TOPIC, that.TOPIC)
                && Objects.equals(TOPIC_HANDLER, that.TOPIC_HANDLER));
    }

    @Override
    public int hashCode() {
        return (Objects.hash(TOPIC, TOPIC_HANDLER));
    }

    @Override
    public String toString() {
        return ("TopicHandler{" +
                "TOPIC=" + TOPIC +
                ", SUBSCRIBER_WORKER=" + TOPIC_HANDLER +
                '}');
    }
}
