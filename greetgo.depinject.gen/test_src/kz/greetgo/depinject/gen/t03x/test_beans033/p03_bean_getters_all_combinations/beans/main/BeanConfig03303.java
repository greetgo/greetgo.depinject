package kz.greetgo.depinject.gen.t03x.test_beans033.p03_bean_getters_all_combinations.beans.main;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.t03x.test_beans033.p03_bean_getters_all_combinations.beans.group1.BeanConfig03303_1;
import kz.greetgo.depinject.gen.t03x.test_beans033.p03_bean_getters_all_combinations.beans.group2.BeanConfig03303_2;
import kz.greetgo.depinject.gen.t03x.test_beans033.p03_bean_getters_all_combinations.beans.simple.BeanConfigSimple03303;

@BeanConfig
@BeanScanner
@Include({
    BeanConfig03303_1.class,
    BeanConfig03303_2.class,
    BeanConfigSimple03303.class,
})
public class BeanConfig03303 {}
