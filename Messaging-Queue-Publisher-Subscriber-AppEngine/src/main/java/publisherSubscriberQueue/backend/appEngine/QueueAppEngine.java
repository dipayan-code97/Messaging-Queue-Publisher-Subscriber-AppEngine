package publisherSubscriberQueue.backend.appEngine;

import publisherSubscriberQueue.backend.abstractorInterfaceChannel.PublisherSubscriberQueue;
import publisherSubscriberQueue.backend.model.Message;
import publisherSubscriberQueue.backend.model.Topic;
import publisherSubscriberQueue.backend.queueAlgorithm.SleepingSubscriber;
import java.sql.Time;

public class QueueAppEngine {

    public static void main(String[] args) throws InterruptedException {

        final PublisherSubscriberQueue pubSubQueue = new PublisherSubscriberQueue();
        final Topic topicSeries1 = pubSubQueue.createTopic("topic-1");
        final Topic topicSeries2 = pubSubQueue.createTopic("topic-2");
        final SleepingSubscriber subscriberSeries1 = new SleepingSubscriber("subscriber1", Time.valueOf(String.valueOf(10_000)));
        final SleepingSubscriber subscriberSeries2 = new SleepingSubscriber("subscriber2", Time.valueOf(String.valueOf(10_000)));

        pubSubQueue.subscribe(subscriberSeries1, topicSeries1);
        pubSubQueue.subscribe(subscriberSeries2, topicSeries1);

        final SleepingSubscriber subscriber3 = new SleepingSubscriber("subscriber3", Time.valueOf(String.valueOf(5_000)));
        pubSubQueue.subscribe(subscriber3, topicSeries2);

        pubSubQueue.publish(topicSeries1, new Message("message-1"));
        pubSubQueue.publish(topicSeries1, new Message("message-2"));

        pubSubQueue.publish(topicSeries2, new Message("message-3"));

        Thread.sleep(15_000);
        pubSubQueue.publish(topicSeries2, new Message("message-4"));
        pubSubQueue.publish(topicSeries1, new Message("message-5"));
        pubSubQueue.resetOffset(topicSeries1, subscriberSeries1, 0);
    }
}
