package kz.greetgo.depinject.gen.test_beans027.beans;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.test_beans027.beans.afterInject_sync.BeanConfigAfterInjectSync;
import kz.greetgo.depinject.gen.test_beans027.beans.groupA.ConfigA;
import kz.greetgo.depinject.gen.test_beans027.beans.groupB.ConfigB;

@BeanConfig
@Include({ConfigA.class, ConfigB.class, BeanConfigAfterInjectSync.class})
public class BeanConfig027 {}
