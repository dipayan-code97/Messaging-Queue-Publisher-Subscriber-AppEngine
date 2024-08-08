Low Level System Design - PubSub Messaging Queue

Project requirements
JDK 1.8
Maven
APIs supported in publisherSubscriberQueue
createTopic(topicName) -> topicId
subscribe(topicId, subscriber) -> boolean
publish(topicId, message) -> boolean
readOffset(topidId, subscriber, offset) -> boolean
