package kz.greetgo.depinject.gen.beans.groupB;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.beans.using_default_factory.BeanConfigWithDefaultFactory;

@BeanConfig
@BeanScanner
@Include({BeanConfigWithDefaultFactory.class})
public class ConfigB {
}
