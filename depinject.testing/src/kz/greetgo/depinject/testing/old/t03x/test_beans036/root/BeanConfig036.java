package kz.greetgo.depinject.testing.old.t03x.test_beans036.root;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.BeanScanner;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.old.t03x.test_beans036.beans4.BeanConfig036_4;
import kz.greetgo.depinject.testing.old.t03x.test_beans036.beans5.BeanConfig036_5;

@BeanConfig
@BeanScanner
@Include({BeanConfig036_4.class, BeanConfig036_5.class})
public class BeanConfig036 {}
