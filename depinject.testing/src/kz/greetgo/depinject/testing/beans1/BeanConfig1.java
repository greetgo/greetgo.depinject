package kz.greetgo.depinject.testing.beans1;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.BeanScanner;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.beans2.BeanConfig2;

@BeanConfig
@BeanScanner
@Include(BeanConfig2.class)
public class BeanConfig1 {}
