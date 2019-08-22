package kz.greetgo.depinject.ap.message;

public abstract class Message extends RuntimeException {

  abstract public MessageLevel getLevel();

  public Message(String message) {
    super(message);
  }
}
