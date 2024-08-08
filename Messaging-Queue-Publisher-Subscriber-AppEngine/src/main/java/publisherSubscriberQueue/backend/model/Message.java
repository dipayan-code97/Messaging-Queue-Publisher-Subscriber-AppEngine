package publisherSubscriberQueue.backend.model;

import lombok.NonNull;
import java.util.Objects;

public final class Message {

    private String textMessageSerialCode;
    private String textMessage;

    public Message() {
    }

    public Message(@NonNull final String textMessageSerialCode,
                   @NonNull final String textMessage) {
        this.textMessageSerialCode = textMessageSerialCode;
        this.textMessage = textMessage;
    }

    public String getTextMessageSerialCode() {
        return this.textMessageSerialCode;
    }

    public String getTextMessage() {
        return this.textMessage;
    }

    public void setTextMessageSerialCode(String textMessageSerialCode) {
        this.textMessageSerialCode = textMessageSerialCode;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    @Override
    public boolean equals(Object objRef) {
        if (this == objRef) return true;
        if (!(objRef instanceof Message)) return false;
        Message message = (Message) objRef;
        return Objects.equals(getTextMessage(), message.getTextMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTextMessage());
    }

    @Override
    public String toString() {
        return ("Message{" +
                "TextMessage='" + textMessage + '\'' +
                '}');
    }
}
