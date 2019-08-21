package kz.greetgo.depinject.testing.old.t02x.test_beans029;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.old.t02x.test_beans029.beans1.BeanConfig029_1;
import kz.greetgo.depinject.testing.old.t02x.test_beans029.beans2.BeanConfig029_2;

@BeanConfig
@Include({BeanConfig029_1.class, BeanConfig029_2.class})
public class BeanConfig029 {}
