package kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.using_default_factory;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.BeanScanner;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.groupB.DefaultInterfaceFactory;

@BeanScanner
@BeanConfig(factory = DefaultInterfaceFactory.class)
public class BeanConfigWithDefaultFactory {}
