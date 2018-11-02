package kz.greetgo.depinject.gen.t03x.test_beans030.beans1;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.ScanPackage;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.t03x.test_beans030.beans3.BeanConfig030_3;

@BeanConfig
@BeanScanner
@Include(BeanConfig030_3.class)
@ScanPackage("^.beans4")
public class BeanConfig030_1 {}
