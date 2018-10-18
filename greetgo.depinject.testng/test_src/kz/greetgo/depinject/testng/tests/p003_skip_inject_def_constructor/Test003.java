package kz.greetgo.depinject.testng.tests.p003_skip_inject_def_constructor;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.tests.p003_skip_inject_def_constructor.beans.BeanConfigTest003;
import kz.greetgo.depinject.testng.tests.p003_skip_inject_def_constructor.beans.BeanTest003_main;
import org.testng.annotations.Test;

@ContainerConfig(BeanConfigTest003.class)
public class Test003 extends AbstractDepinjectTestNg {

  public BeanGetter<BeanTest003_main> beanTest002_main;

  @Test
  public void skip_inject_def_constructor() {

    beanTest002_main.get().assertRefNull();

  }
}
