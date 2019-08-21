package kz.greetgo.depinject.testing.launcher;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.testing.beans1.BeanConfig1;
import kz.greetgo.depinject.testing.beans1.Hello1;

@BeanContainer(implPostfix = "Impl")
@Include(BeanConfig1.class)
public interface ExampleContainer {
  Hello1 hello1();
}
