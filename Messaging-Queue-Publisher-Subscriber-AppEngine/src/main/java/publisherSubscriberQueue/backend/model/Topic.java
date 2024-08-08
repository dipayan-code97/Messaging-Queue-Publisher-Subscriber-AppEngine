package publisherSubscriberQueue.backend.model;

import lombok.NonNull;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Topic {
    private final String TOPIC_NAME;
    private final String TOPIC_SERIAL_CODE;
    private final Set<Message> TEXT_MESSAGE; // TODO: Change getter this to send only immutable list outside.
    private final Set<TopicSubscriber> TOPIC_SUBSCRIBER; // TODO: Change getter this to send only immutable list outside.
    public Topic(@NonNull final String TOPIC_NAME, @NonNull final String TOPIC_SERIAL_CODE) {
        this.TOPIC_NAME = TOPIC_NAME;
        this.TOPIC_SERIAL_CODE = TOPIC_SERIAL_CODE;
        this.TEXT_MESSAGE = new LinkedHashSet<>();
        this.TOPIC_SUBSCRIBER = new LinkedHashSet<>();
    }

    public String getTopicName() {
        return this.TOPIC_NAME;
    }

    public String getTopicSerialCode() {
        return this.TOPIC_SERIAL_CODE;
    }

    public Set<Message> getTextMessage() {
        return this.TEXT_MESSAGE;
    }

    public Set<TopicSubscriber> getTopicSubscriber() {
        return this.TOPIC_SUBSCRIBER;
    }

    public synchronized void addMessage(@NonNull final Message message) {
        TEXT_MESSAGE.add(message);
    }

    public void addSubscriber(@NonNull final TopicSubscriber subscriber) {
        TOPIC_SUBSCRIBER.add(subscriber);
    }

    @Override
    public boolean equals(Object objRef) {
        if (this == objRef) return true;
        if (!(objRef instanceof Topic)) return false;
        Topic topic = (Topic) objRef;
        return (Objects.equals(TOPIC_NAME, topic.TOPIC_NAME)
                && Objects.equals(TOPIC_SERIAL_CODE, topic.TOPIC_SERIAL_CODE)
                && Objects.equals(TEXT_MESSAGE, topic.TEXT_MESSAGE)
                && Objects.equals(TOPIC_SUBSCRIBER, topic.TOPIC_SUBSCRIBER));
    }

    @Override
    public int hashCode() {
        return (Objects.hash(TOPIC_NAME,
                TOPIC_SERIAL_CODE,
                TEXT_MESSAGE,
                TOPIC_SUBSCRIBER));
    }

    @Override
    public String toString() {
        return ("Topic{" +
                "TOPIC_NAME='" + TOPIC_NAME + '\'' +
                ", TOPIC_SERIAL_CODE='" + TOPIC_SERIAL_CODE + '\'' +
                ", TEXT_MESSAGE=" + TEXT_MESSAGE +
                ", TOPIC_SUBSCRIBER=" + TOPIC_SUBSCRIBER +
                '}');
    }
}
