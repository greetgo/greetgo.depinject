package kz.greetgo.depinject.testng.complex_tests.BeanPreparation;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.complex_tests.BeanPreparation.beans.BeanConfigBeanPreparation;
import kz.greetgo.depinject.testng.complex_tests.BeanPreparation.beans.BeanPreparationRoom;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfigBeanPreparation.class)
public class BeanPreparationTest extends AbstractDepinjectTestNg {
  public static final List<String> log = new ArrayList<>();

  public BeanGetter<BeanPreparationRoom> room;

  @Test
  public void test() throws Exception {

    log.clear();
    log.add("--- Start");

    room.get().lookOutWindow();

    room.get().lookOutWindowImpl();

    log.forEach(System.out::println);

    assertThat(log.get(0)).isEqualTo("--- Start");
  }
}
