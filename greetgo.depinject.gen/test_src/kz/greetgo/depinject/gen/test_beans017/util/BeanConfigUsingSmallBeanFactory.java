package kz.greetgo.depinject.gen.test_beans017.util;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.test_beans017.beans.bean_factory_big.BeanConfigSmallBeanFactory;
import kz.greetgo.depinject.gen.test_beans017.beans.core.BeanConfigUsingBeanFactory;

@BeanConfig
@Include({BeanConfigUsingBeanFactory.class, BeanConfigSmallBeanFactory.class})
public class BeanConfigUsingSmallBeanFactory {}
