package kz.greetgo.depinject.testing.launcher;

import kz.greetgo.depinject.testing.beans1.Hello1;

public class Example {
  public static void main(String[] args) throws Exception {
    ExampleContainer container = (ExampleContainer)
      Class.forName(ExampleContainer.class.getName() + "Impl")
        .getDeclaredConstructor()
        .newInstance();

    Hello1 hello1 = container.hello1();

    hello1.hello();


  }
}
