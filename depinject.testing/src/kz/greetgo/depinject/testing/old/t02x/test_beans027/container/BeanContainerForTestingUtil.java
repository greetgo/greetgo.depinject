package kz.greetgo.depinject.testing.old.t02x.test_beans027.container;

import kz.greetgo.depinject.ann.BeanContainer;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.BeanConfig027;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.afterInject_sync.DependsOnAlone;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.interfaces.IBeanB1;

@Include(BeanConfig027.class)
public interface BeanContainerForTestingUtil extends BeanContainer {
  IBeanB1 getIBeanB1();

  DependsOnAlone getDependsOnAlone();
}
