package kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans3;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans2_1.BeanConfig005_2_1;
import kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans2_2.BeanConfig005_2_2;

@BeanConfig
@BeanScanner
@Include({BeanConfig005_2_1.class, BeanConfig005_2_2.class})
public class BeanConfig005_3 {}
