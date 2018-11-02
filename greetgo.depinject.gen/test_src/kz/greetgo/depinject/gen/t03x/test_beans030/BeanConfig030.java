package kz.greetgo.depinject.gen.t03x.test_beans030;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.t03x.test_beans030.beans1.BeanConfig030_1;
import kz.greetgo.depinject.gen.t03x.test_beans030.beans2.BeanConfig030_2;

@BeanConfig
@Include({BeanConfig030_1.class, BeanConfig030_2.class})
public class BeanConfig030 {}
