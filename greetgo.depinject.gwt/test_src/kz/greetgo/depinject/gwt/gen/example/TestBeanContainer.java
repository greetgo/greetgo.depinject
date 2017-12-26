package kz.greetgo.depinject.gwt.gen.example;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;

@Include(TotalConfig.class)
public interface TestBeanContainer extends BeanContainer {
  AsdBean getAsdBean();
}
