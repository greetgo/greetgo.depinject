package kz.greetgo.depinject.testing.old.t03x.test_beans031.beans1;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.BeanScanner;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.ann.ScanPackage;
import kz.greetgo.depinject.testing.old.t03x.test_beans031.beans3.BeanConfig031_3;

@BeanConfig
@BeanScanner
@Include(BeanConfig031_3.class)
@ScanPackage("^.beans4")
public class BeanConfig031_1 {
}
