package kz.greetgo.depinject.testng.tests.p002_private_bg_def_constructor;

import kz.greetgo.depinject.Depinject;
import kz.greetgo.depinject.gen.DepinjectUtil;
import kz.greetgo.depinject.gen.errors.NotPublicBeanWithoutConstructor;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.tests.p002_private_bg_def_constructor.beans.BeanConfigTest002;
import kz.greetgo.depinject.testng.tests.p002_private_bg_def_constructor.beans.BeanTest002_main;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfigTest002.class)
public class Test002 extends AbstractDepinjectTestNg {
  @Test
  public void private_bg_def_constructor() throws Exception {

    NotPublicBeanWithoutConstructor error = null;

    try {
      DepinjectUtil.implementAndUseBeanContainers(
        BeanContainerTest002.class.getPackage().getName(),
        "build/tests_data/" + getClass().getName());
    } catch (NotPublicBeanWithoutConstructor e) {
      error = e;
    }

    assertThat(error).isNotNull();
    assert error != null;

    assertThat(error.beanGetterField.getName()).isEqualTo("beanTest002_ref");
    assertThat(error.containsFieldClass.getName()).isEqualTo(BeanTest002_main.class.getName());
    assertThat(error.beanClass).isNull();

  }
}
