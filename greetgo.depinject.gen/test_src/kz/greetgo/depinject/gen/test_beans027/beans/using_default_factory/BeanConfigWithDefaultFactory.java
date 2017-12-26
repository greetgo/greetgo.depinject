package kz.greetgo.depinject.gen.test_beans027.beans.using_default_factory;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.gen.test_beans027.beans.groupB.DefaultInterfaceFactory;

@BeanScanner
@BeanConfig(defaultFactoryClass = DefaultInterfaceFactory.class)
public class BeanConfigWithDefaultFactory {}
