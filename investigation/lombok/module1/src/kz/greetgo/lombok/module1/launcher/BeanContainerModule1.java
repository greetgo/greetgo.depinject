package kz.greetgo.lombok.module1.launcher;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.lombok.module1.beans.BeanConfigModule1;
import kz.greetgo.lombok.module1.beans.Module1;

@Include(BeanConfigModule1.class)
public interface BeanContainerModule1 extends BeanContainer {
  Module1 module1();
}
