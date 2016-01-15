package kz.greetgo.depinject.gwt.stand.src;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gwt.stand.src.somebeans.HereBeanConfig;

@Include(HereBeanConfig.class)
public interface TestBeanContainer extends BeanContainer {
  AsdInterface getAsd();
}
