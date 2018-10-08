package kz.greetgo.kotline_probe.main.launcher;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.kotline_probe.main.beans.BeanConfigMain;
import kz.greetgo.kotline_probe.main.beans.MainBean;

@Include(BeanConfigMain.class)
public interface BeanContainerMain extends BeanContainer {
  MainBean main();
}
