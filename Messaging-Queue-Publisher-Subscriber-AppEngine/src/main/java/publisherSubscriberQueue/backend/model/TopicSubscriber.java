package publisherSubscriberQueue.backend.model;

import publisherSubscriberQueue.backend.abstractorInterfaceChannel.ISubscriber;
import lombok.NonNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class TopicSubscriber {
    private final AtomicInteger OFFSET;
    private final ISubscriber SUBSCRIBER;

    public TopicSubscriber(@NonNull final ISubscriber subscriber) {
        this.SUBSCRIBER = subscriber;
        this.OFFSET = new AtomicInteger(0);
    }

    public AtomicInteger getOffset() {
        return this.OFFSET;
    }

    public ISubscriber getSubscriber() {
        return this.SUBSCRIBER;
    }

    @Override
    public boolean equals(Object objRef) {
        if (this == objRef) return true;
        if (!(objRef instanceof TopicSubscriber)) return false;
        TopicSubscriber that = (TopicSubscriber) objRef;
        return (Objects.equals(getOffset(), that.getOffset())
                && Objects.equals(getSubscriber(), that.getSubscriber()));
    }

    @Override
    public int hashCode() {
        return (Objects.hash(getOffset(), getSubscriber()));
    }

    @Override
    public String toString() {
        return ("TopicSubscriber{" +
                "Offset=" + OFFSET +
                ", Subscriber=" + SUBSCRIBER +
                '}');
    }
}
