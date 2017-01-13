package kz.greetgo.depinject.gen2.test_beans017.util;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen2.test_beans017.beans.bean_factory_small.BeanConfigBigBeanFactory;
import kz.greetgo.depinject.gen2.test_beans017.beans.core.BeanConfigUsingBeanFactory;

@BeanConfig
@Include({BeanConfigUsingBeanFactory.class, BeanConfigBigBeanFactory.class})
public class BeanConfigUsingBigBeanFactory {
}
