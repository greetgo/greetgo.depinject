package kz.greetgo.depinject.testng.tests.p004_include_from_another_config;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.tests.p004_include_from_another_config.beans2.Bean004_2;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfig004.class)
public class Test004 extends AbstractDepinjectTestNg {

  public BeanGetter<Bean004> bean004;

  @Test
  public void include_from_another_config() {

    String value = RND.str(10);

    bean004.get().setValue(value);

    assertThat(Bean004_2.value).isEqualTo(value);
  }
}
