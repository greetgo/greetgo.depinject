package kz.greetgo.depinject.testing.old.t00x.test_beans005.sub_beans_2;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.BeanScanner;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.old.t00x.test_beans005.sub_beans_3.BeanConfig005_03;

@BeanScanner
@Include(BeanConfig005_03.class)
@BeanConfig(factory = BeanFactory2.class)
public class BeanConfig005_02 {}
