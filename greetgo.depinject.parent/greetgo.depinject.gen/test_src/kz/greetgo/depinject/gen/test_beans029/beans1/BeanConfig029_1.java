package kz.greetgo.depinject.gen.test_beans029.beans1;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.ScanPackage;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.test_beans029.beans3.BeanConfig029_3;

@BeanConfig
@BeanScanner
@Include(BeanConfig029_3.class)
@ScanPackage("^.beans4")
public class BeanConfig029_1 {}
