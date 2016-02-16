package kz.greetgo.depinject.testng.SomeTestBeans;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.testng.SomeTestBeans.beans001.Beans001config;
import kz.greetgo.depinject.testng.SomeTestBeans.beans002.Beans002config;

@BeanConfig
@Include({Beans001config.class, Beans002config.class})
public class SomeTestBeans {
}
