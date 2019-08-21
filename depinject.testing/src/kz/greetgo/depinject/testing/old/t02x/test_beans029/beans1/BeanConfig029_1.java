package kz.greetgo.depinject.testing.old.t02x.test_beans029.beans1;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.BeanScanner;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.old.t02x.test_beans029.beans3.BeanConfig029_3;

@SuppressWarnings("deprecation")
@BeanConfig
@BeanScanner
@Include(BeanConfig029_3.class)
@kz.greetgo.depinject.ann.ScanPackage("^.beans4")
public class BeanConfig029_1 {}
