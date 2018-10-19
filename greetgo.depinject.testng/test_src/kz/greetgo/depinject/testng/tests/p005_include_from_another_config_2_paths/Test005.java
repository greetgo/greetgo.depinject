package kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans1.Bean005_1;
import kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans2_1.Bean005_2_1;
import kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans2_2.Bean005_2_2;
import kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans3.Bean005_3;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfig005.class)
public class Test005 extends AbstractDepinjectTestNg {

  public BeanGetter<Bean005_3> bean005_3;

  @Test
  public void include_from_another_config_2_paths() {

    assertThat(Bean005_1.VALUE_LIST).isEmpty();

    String value1 = RND.str(10);

    assertThat(Bean005_2_1.instanceCount).isZero();
    assertThat(Bean005_2_2.instanceCount).isZero();

    bean005_3.get().addValue1(value1);

    assertThat(Bean005_2_1.instanceCount).isEqualTo(1);
    assertThat(Bean005_2_2.instanceCount).isZero();

    assertThat(Bean005_1.VALUE_LIST).containsExactly("init", value1);

    String value2 = RND.str(10);

    bean005_3.get().addValue2(value2);

    assertThat(Bean005_2_1.instanceCount).isEqualTo(1);
    assertThat(Bean005_2_2.instanceCount).isEqualTo(1);

    assertThat(Bean005_1.VALUE_LIST).containsExactly("init", value1, value2);
  }
}
