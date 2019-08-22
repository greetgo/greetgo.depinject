package kz.greetgo.depinject.ann.util.message;

public class NoCandidates extends Message {
  public NoCandidates(String message) {
    super(message);
  }

  @Override
  public MessageLevel getLevel() {
    return MessageLevel.ERROR;
  }
}
