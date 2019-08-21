package kz.greetgo.depinject.testing.old.t01x.test_beans017.beans.core;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.BeanScanner;


@BeanScanner
@BeanConfig(factory = LocalBeanFactory.class)
public class BeanConfigUsingBeanFactory {}
