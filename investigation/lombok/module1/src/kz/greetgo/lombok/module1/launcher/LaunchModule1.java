package kz.greetgo.lombok.module1.launcher;

import kz.greetgo.depinject.Depinject;
import kz.greetgo.lombok.module1.beans.Module1;

public class LaunchModule1 {
  public static void main(String[] args) {
    BeanContainerModule1 beanContainer = Depinject.newInstance(BeanContainerModule1.class);

    Module1 module1 = beanContainer.module1();

    StringBuilder out = new StringBuilder();

    module1.print(out);

    System.out.println(out);
  }
}
