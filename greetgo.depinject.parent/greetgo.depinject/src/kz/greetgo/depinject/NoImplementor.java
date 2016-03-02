package kz.greetgo.depinject;

public class NoImplementor extends RuntimeException {
  public NoImplementor(String message, ClassNotFoundException e) {
    super(message, e);
  }
}
