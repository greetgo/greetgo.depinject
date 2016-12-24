package kz.greetgo.depinject.gen2.test_beans005.sub_beans_2;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen2.test_beans005.sub_beans_3.BeanConfig005_03;

@BeanScanner
@Include(BeanConfig005_03.class)
@BeanConfig(defaultFactoryClass = BeanFactory2.class)
public class BeanConfig005_02 {
}
