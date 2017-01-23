package kz.greetgo.depinject.gen.test_beans31.beans1;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.core.ScanPackage;
import kz.greetgo.depinject.gen.test_beans31.beans3.BeanConfig031_3;

@BeanConfig
@BeanScanner
@Include(BeanConfig031_3.class)
@ScanPackage("^.beans4")
public class BeanConfig031_1 {
}
