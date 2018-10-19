package kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans3.BeanConfig005_3;

@BeanConfig
@Include(BeanConfig005_3.class)
public class BeanConfig005 {}
