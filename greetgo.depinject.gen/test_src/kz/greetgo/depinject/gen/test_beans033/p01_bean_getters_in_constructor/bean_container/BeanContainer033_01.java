package kz.greetgo.depinject.gen.test_beans033.p01_bean_getters_in_constructor.bean_container;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.test_beans033.p01_bean_getters_in_constructor.beans.BeanConfig033_01;
import kz.greetgo.depinject.gen.test_beans033.p01_bean_getters_in_constructor.beans.MainBean033_01;

@Include(BeanConfig033_01.class)
public interface BeanContainer033_01 extends BeanContainer {
  MainBean033_01 mainBean();
}
