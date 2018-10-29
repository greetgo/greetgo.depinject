package kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.bean_container;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.main.BeanConfig03303;
import kz.greetgo.depinject.gen.test_beans033.p03_bean_getters_all_combinations.beans.main.MainBean03303;

@Include(BeanConfig03303.class)
public interface BeanContainer03303 extends BeanContainer {
  MainBean03303 mainBean();
}
