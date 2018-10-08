package kz.greetgo.kotline_probe.main.beans;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.kotlin_probe.module1.BeanConfigModule1;

@BeanConfig
@BeanScanner
@Include(BeanConfigModule1.class)
public class BeanConfigMain {}
