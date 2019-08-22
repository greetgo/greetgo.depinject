package kz.greetgo.depinject.ann.util.message;

public class ManyCandidates extends Message {
  public ManyCandidates(String message) {
    super(message);
  }

  @Override
  public MessageLevel getLevel() {
    return MessageLevel.ERROR;
  }
}
