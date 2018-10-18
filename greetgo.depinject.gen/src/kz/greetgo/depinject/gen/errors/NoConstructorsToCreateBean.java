package kz.greetgo.depinject.gen.errors;

public class NoConstructorsToCreateBean extends RuntimeException {
  public NoConstructorsToCreateBean(Class<?> beanClass) {
    super("for " + beanClass + "\n\tDepinject can access only to public constructors.\n" +
        "\tCheck public access to constructor you want to use to create bean with depinject.\n");
  }
}
