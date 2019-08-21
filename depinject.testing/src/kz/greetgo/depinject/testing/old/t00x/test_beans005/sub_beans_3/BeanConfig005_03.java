package kz.greetgo.depinject.testing.old.t00x.test_beans005.sub_beans_3;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.BeanScanner;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.old.t00x.test_beans005.sub_beans_4.BeanConfig005_04;

@BeanConfig
@BeanScanner
@Include(BeanConfig005_04.class)
public class BeanConfig005_03 {}
