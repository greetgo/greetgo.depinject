package kz.greetgo.depinject.testing.old.t03x.test_beans031;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.old.t03x.test_beans031.beans1.BeanConfig031_1;
import kz.greetgo.depinject.testing.old.t03x.test_beans031.beans2.BeanConfig031_2;

@BeanConfig
@Include({BeanConfig031_1.class, BeanConfig031_2.class})
public class BeanConfig031 {}
