package kz.greetgo.tests.run;

import kz.greetgo.depinject.Depinject;

public class Main {
  public static void main(String[] args) {
    final Container container = Depinject.newInstance(Container.class);
    container.bean2().hello();
  }
}
