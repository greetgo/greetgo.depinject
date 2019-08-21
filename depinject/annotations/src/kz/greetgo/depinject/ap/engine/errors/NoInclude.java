package kz.greetgo.depinject.ap.engine.errors;

public class NoInclude extends RuntimeException {

  public final Class<?> mustContainingClass;

  public NoInclude(Class<?> mustContainingClass) {
    super("In " + mustContainingClass.toString());
    this.mustContainingClass = mustContainingClass;
  }
}
