package kz.greetgo.depinject.gen.beans;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.beans.groupA.ConfigA;
import kz.greetgo.depinject.gen.beans.groupB.ConfigB;

@BeanConfig
@Include({ConfigA.class, ConfigB.class})
public class MainConfig {
}
