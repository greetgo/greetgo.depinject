package kz.greetgo.depinject.ap.engine.errors;

public class LeftException extends RuntimeException {
  public LeftException(String checkpoint) {
    super(checkpoint);
  }
}
