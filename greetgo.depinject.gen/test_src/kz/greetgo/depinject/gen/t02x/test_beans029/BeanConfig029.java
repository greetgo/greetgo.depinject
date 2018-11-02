package kz.greetgo.depinject.gen.t02x.test_beans029;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.t02x.test_beans029.beans1.BeanConfig029_1;
import kz.greetgo.depinject.gen.t02x.test_beans029.beans2.BeanConfig029_2;

@BeanConfig
@Include({BeanConfig029_1.class, BeanConfig029_2.class})
public class BeanConfig029 {}
