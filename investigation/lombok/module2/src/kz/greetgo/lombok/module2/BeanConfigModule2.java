package kz.greetgo.lombok.module2;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.lombok.module3.BeanConfigModule3;

@BeanConfig
@BeanScanner
@Include(BeanConfigModule3.class)
public class BeanConfigModule2 {}
