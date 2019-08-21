package kz.greetgo.depinject.ap.message;

public class ManyCandidates implements Message {
  @Override
  public MessageLevel getLevel() {
    return MessageLevel.ERROR;
  }
}
