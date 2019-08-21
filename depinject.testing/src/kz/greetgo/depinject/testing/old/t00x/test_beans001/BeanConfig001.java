package kz.greetgo.depinject.testing.old.t00x.test_beans001;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.old.t00x.test_beans001.p1.BeanConfig001_01;
import kz.greetgo.depinject.testing.old.t00x.test_beans001.p2.BeanConfig001_02;
import kz.greetgo.depinject.testing.old.t00x.test_beans001.p1.BeanConfig001_01;

@BeanConfig
@Include({BeanConfig001_01.class, BeanConfig001_02.class})
public class BeanConfig001 {}
