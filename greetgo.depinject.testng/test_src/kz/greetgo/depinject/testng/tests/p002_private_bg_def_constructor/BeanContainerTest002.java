package kz.greetgo.depinject.testng.tests.p002_private_bg_def_constructor;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.testng.tests.p002_private_bg_def_constructor.beans.BeanConfigTest002;
import kz.greetgo.depinject.testng.tests.p002_private_bg_def_constructor.beans.BeanTest002_main;

@Include(BeanConfigTest002.class)
public interface BeanContainerTest002 extends BeanContainer {
  @SuppressWarnings("unused")
  BeanTest002_main main();
}
