package kz.greetgo.depinject.gen.t02x.test_beans027.beans;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.t02x.test_beans027.beans.afterInject_sync.BeanConfigAfterInjectSync;
import kz.greetgo.depinject.gen.t02x.test_beans027.beans.groupB.ConfigB;
import kz.greetgo.depinject.gen.t02x.test_beans027.beans.groupA.ConfigA;

@BeanConfig
@Include({ConfigA.class, ConfigB.class, BeanConfigAfterInjectSync.class})
public class BeanConfig027 {}
