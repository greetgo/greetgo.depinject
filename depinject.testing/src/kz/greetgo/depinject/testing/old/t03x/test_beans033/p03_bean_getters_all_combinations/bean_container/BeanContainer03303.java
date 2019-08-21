package kz.greetgo.depinject.testing.old.t03x.test_beans033.p03_bean_getters_all_combinations.bean_container;

import kz.greetgo.depinject.ann.BeanContainer;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.old.t03x.test_beans033.p03_bean_getters_all_combinations.beans.main.BeanConfig03303;
import kz.greetgo.depinject.testing.old.t03x.test_beans033.p03_bean_getters_all_combinations.beans.main.MainBean03303;

@Include(BeanConfig03303.class)
public interface BeanContainer03303 extends BeanContainer {
  MainBean03303 mainBean();
}
