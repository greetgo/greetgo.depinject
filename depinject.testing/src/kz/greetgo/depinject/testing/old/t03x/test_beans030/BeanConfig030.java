package kz.greetgo.depinject.testing.old.t03x.test_beans030;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.old.t03x.test_beans030.beans1.BeanConfig030_1;
import kz.greetgo.depinject.testing.old.t03x.test_beans030.beans2.BeanConfig030_2;

@BeanConfig
@Include({BeanConfig030_1.class, BeanConfig030_2.class})
public class BeanConfig030 {}
