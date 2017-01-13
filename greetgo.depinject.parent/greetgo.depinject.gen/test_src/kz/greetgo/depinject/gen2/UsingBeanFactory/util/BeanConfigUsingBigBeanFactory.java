package kz.greetgo.depinject.gen2.UsingBeanFactory.util;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen2.UsingBeanFactory.beans.bean_factory_small.BeanConfigBigBeanFactory;
import kz.greetgo.depinject.gen2.UsingBeanFactory.beans.core.BeanConfigUsingBeanFactory;

@BeanConfig
@Include({BeanConfigUsingBeanFactory.class, BeanConfigBigBeanFactory.class})
public class BeanConfigUsingBigBeanFactory {
}
