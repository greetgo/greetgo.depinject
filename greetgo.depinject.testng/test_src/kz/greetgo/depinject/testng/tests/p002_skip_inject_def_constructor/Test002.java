package kz.greetgo.depinject.testng.tests.p002_skip_inject_def_constructor;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.tests.p002_skip_inject_def_constructor.beans.BeanConfigTest002;
import kz.greetgo.depinject.testng.tests.p002_skip_inject_def_constructor.beans.BeanTest002_main;
import org.testng.annotations.Test;

@ContainerConfig(BeanConfigTest002.class)
public class Test002 extends AbstractDepinjectTestNg {

  public BeanGetter<BeanTest002_main> beanTest002_main;

  @Test
  public void skip_inject_def_constructor() {

    beanTest002_main.get().assertRefNull();

  }
}
