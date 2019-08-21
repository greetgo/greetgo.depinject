package kz.greetgo.depinject.testing.old.t03x.test_beans033.p02_list_bean_getters_in_constructor.bean_container;

import kz.greetgo.depinject.ann.BeanContainer;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.old.t03x.test_beans033.p02_list_bean_getters_in_constructor.beans.BeanConfig03302;
import kz.greetgo.depinject.testing.old.t03x.test_beans033.p02_list_bean_getters_in_constructor.beans.MainBean03302;

@Include(BeanConfig03302.class)
public interface BeanContainer03302 extends BeanContainer {
  MainBean03302 mainBean();
}
