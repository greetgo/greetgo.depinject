package kz.greetgo.depinject.stand.src;

import kz.greetgo.depinject.src.BeanContainer;
import kz.greetgo.depinject.src.Include;
import kz.greetgo.depinject.stand.src.somebeans.HereBeanConfig;

@Include(HereBeanConfig.class)
public interface TestBeanContainer extends BeanContainer {
  AsdIface getAsd();
}
