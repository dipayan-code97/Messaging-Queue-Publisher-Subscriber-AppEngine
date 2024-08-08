package publisherSubscriberQueue.backend.abstractorInterfaceChannel;

import lombok.NonNull;
import publisherSubscriberQueue.backend.model.Message;
import publisherSubscriberQueue.backend.model.Topic;

public interface ISubscriber {

    String getSubscriberSerialCode();
    void consume(@NonNull final Message TEXT_MESSAGE) throws InterruptedException;
    Topic createTopic(@NonNull final String TOPIC_NAME);

    void subscribe(@NonNull final ISubscriber SUBSCRIBER,
              @NonNull final Topic TOPIC);

    void publish(@NonNull final Topic TOPIC,
                 @NonNull final Message MESSAGE);

    void resetOffset(@NonNull final Topic TOPIC, @NonNull final ISubscriber SUBSCRIBER,
                     @NonNull final Integer NEW_OFFSET);
}
