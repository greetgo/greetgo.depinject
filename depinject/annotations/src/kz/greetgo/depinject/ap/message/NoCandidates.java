package kz.greetgo.depinject.ap.message;

public class NoCandidates implements Message {
  @Override
  public MessageLevel getLevel() {
    return MessageLevel.ERROR;
  }
}
