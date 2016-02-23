package kz.greetgo.depinject.gen.impl_and_use_bean_containers;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.beans.MainConfig;
import kz.greetgo.depinject.gen.interfaces.IBeanB1;

@Include(MainConfig.class)
public interface BeanContainerForTestingUtil extends BeanContainer {
  IBeanB1 getIBeanB1();
}
