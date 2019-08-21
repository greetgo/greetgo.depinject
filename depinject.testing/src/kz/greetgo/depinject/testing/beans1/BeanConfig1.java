package kz.greetgo.depinject.testing.beans1;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.testing.beans2.BeanConfig2;

@BeanConfig
@BeanScanner
@Include(BeanConfig2.class)
public class BeanConfig1 {}
