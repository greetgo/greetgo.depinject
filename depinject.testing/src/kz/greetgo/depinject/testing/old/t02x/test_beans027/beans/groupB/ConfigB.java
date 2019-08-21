package kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.groupB;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.BeanScanner;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.using_default_factory.BeanConfigWithDefaultFactory;

@BeanConfig
@BeanScanner
@Include({BeanConfigWithDefaultFactory.class})
public class ConfigB {}
