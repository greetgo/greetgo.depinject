package kz.greetgo.depinject.gen2.UsingBeanFactory.util;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen2.UsingBeanFactory.beans.bean_factory_big.BeanConfigSmallBeanFactory;
import kz.greetgo.depinject.gen2.UsingBeanFactory.beans.core.BeanConfigUsingBeanFactory;

@BeanConfig
@Include({BeanConfigUsingBeanFactory.class, BeanConfigSmallBeanFactory.class})
public class BeanConfigUsingSmallBeanFactory {
}
