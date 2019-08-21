package kz.greetgo.depinject.testing.old.t02x.test_beans027.beans;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.Include;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.afterInject_sync.BeanConfigAfterInjectSync;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.groupA.ConfigA;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.groupB.ConfigB;

@BeanConfig
@Include({ConfigA.class, ConfigB.class, BeanConfigAfterInjectSync.class})
public class BeanConfig027 {}
