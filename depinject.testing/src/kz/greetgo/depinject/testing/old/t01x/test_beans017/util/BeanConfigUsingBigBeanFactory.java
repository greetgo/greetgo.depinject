package kz.greetgo.depinject.testing.old.t01x.test_beans017.util;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.old.t01x.test_beans017.beans.bean_factory_small.BeanConfigBigBeanFactory;
import kz.greetgo.depinject.testing.old.t01x.test_beans017.beans.core.BeanConfigUsingBeanFactory;

@BeanConfig
@Include({BeanConfigUsingBeanFactory.class, BeanConfigBigBeanFactory.class})
public class BeanConfigUsingBigBeanFactory {}
