package kz.greetgo.depinject.gen.test_beans001;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.test_beans001.p1.BeanConfig001_01;
import kz.greetgo.depinject.gen.test_beans001.p2.BeanConfig001_02;

@BeanConfig
@Include({BeanConfig001_01.class, BeanConfig001_02.class})
public class BeanConfig001 {
}
