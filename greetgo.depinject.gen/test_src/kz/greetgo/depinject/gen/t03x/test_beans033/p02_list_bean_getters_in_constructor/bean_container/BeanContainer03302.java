package kz.greetgo.depinject.gen.t03x.test_beans033.p02_list_bean_getters_in_constructor.bean_container;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.t03x.test_beans033.p02_list_bean_getters_in_constructor.beans.BeanConfig03302;
import kz.greetgo.depinject.gen.t03x.test_beans033.p02_list_bean_getters_in_constructor.beans.MainBean03302;

@Include(BeanConfig03302.class)
public interface BeanContainer03302 extends BeanContainer {
  MainBean03302 mainBean();
}
