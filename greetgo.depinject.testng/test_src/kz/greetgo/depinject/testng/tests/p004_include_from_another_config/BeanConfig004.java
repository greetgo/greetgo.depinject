package kz.greetgo.depinject.testng.tests.p004_include_from_another_config;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.testng.tests.p004_include_from_another_config.beans1.BeanConfig004_1;
import kz.greetgo.depinject.testng.tests.p004_include_from_another_config.beans2.BeanConfig004_2;

@BeanConfig
@Include({BeanConfig004_1.class, BeanConfig004_2.class})
public class BeanConfig004 {}
