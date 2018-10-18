package kz.greetgo.depinject.testng.test_beans_package;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.ScanPackage;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.testng.test_beans_package.beans001.Beans001config;
import kz.greetgo.depinject.testng.test_beans_package.beans002.Beans002config;

@BeanConfig
@Include({Beans001config.class, Beans002config.class})
@ScanPackage("kz.greetgo.depinject.testng.test_beans_package.for_include_by_str")
public class AbstractDepinjectTestNgTestBeans {}
