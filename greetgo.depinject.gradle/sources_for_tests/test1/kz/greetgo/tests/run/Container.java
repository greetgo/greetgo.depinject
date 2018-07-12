package kz.greetgo.tests.run;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.tests.group1.BeanConfig1;
import kz.greetgo.tests.group2.Bean2;
import kz.greetgo.tests.group2.BeanConfig2;

@Include({BeanConfig1.class, BeanConfig2.class})
public interface Container extends BeanContainer {
  Bean2 bean2();
}
