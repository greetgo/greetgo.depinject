package kz.greetgo.kotlin_probe.module1;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.kotlin_probe.module2.BeanConfigModule2;

@BeanConfig
@BeanScanner
@Include(BeanConfigModule2.class)
public class BeanConfigModule1 {}
