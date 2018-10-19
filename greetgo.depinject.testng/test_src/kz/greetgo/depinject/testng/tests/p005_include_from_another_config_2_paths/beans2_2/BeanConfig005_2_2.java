package kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans2_2;


import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans1.BeanConfig005_1;

@BeanConfig
@BeanScanner
@Include(BeanConfig005_1.class)
public class BeanConfig005_2_2 {}
