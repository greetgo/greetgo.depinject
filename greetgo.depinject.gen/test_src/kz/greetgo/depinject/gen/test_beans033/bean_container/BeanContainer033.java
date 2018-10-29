package kz.greetgo.depinject.gen.test_beans033.bean_container;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.test_beans033.beans.BeanConfig033;
import kz.greetgo.depinject.gen.test_beans033.beans.MainBean033;

@Include(BeanConfig033.class)
public interface BeanContainer033 extends BeanContainer {
  MainBean033 mainBean();
}
