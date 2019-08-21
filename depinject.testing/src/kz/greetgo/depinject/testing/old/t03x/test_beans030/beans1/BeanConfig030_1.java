package kz.greetgo.depinject.testing.old.t03x.test_beans030.beans1;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.BeanScanner;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.ann.ScanPackage;
import kz.greetgo.depinject.testing.old.t03x.test_beans030.beans3.BeanConfig030_3;

@BeanConfig
@BeanScanner
@Include(BeanConfig030_3.class)
@ScanPackage("^.beans4")
public class BeanConfig030_1 {}
