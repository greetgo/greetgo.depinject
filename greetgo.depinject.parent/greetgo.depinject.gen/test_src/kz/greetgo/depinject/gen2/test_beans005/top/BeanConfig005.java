package kz.greetgo.depinject.gen2.test_beans005.top;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen2.test_beans005.sub_beans_1.BeanConfig005_01;
import kz.greetgo.depinject.gen2.test_beans005.sub_beans_2.BeanConfig005_02;

@BeanScanner
@BeanConfig(defaultFactoryClass = TopBeanFactory.class)
@Include({BeanConfig005_01.class, BeanConfig005_02.class})
public class BeanConfig005 {
}
