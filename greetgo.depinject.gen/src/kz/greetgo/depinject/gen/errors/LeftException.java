package kz.greetgo.depinject.gen.errors;

public class LeftException extends RuntimeException {
  public LeftException(String checkpoint) {
    super(checkpoint);
  }
}
