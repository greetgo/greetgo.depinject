package kz.greetgo.depinject.testng.complex_tests.BeanPreparation;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.complex_tests.BeanPreparation.beans.BeanConfigBeanPreparation;
import kz.greetgo.depinject.testng.complex_tests.BeanPreparation.beans.BeanPreparation_Room;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfigBeanPreparation.class)
public class BeanPreparationTest extends AbstractDepinjectTestNg {
  public static final List<String> log = new ArrayList<>();

  public BeanGetter<BeanPreparation_Room> room;

  @Test
  public void test() throws Exception {

    log.clear();
    log.add("--- Start");

    room.get().lookOutWindow();

    log.add("--- Checkpoint");

    room.get().lookOutWindowImpl();

    log.forEach(System.out::println);

    assertThat(log.get(0)).isEqualTo("--- Start");
    assertThat(log.get(1)).isEqualTo("Before look out");
    assertThat(log.get(2)).isEqualTo("Look out window");
    assertThat(log.get(3)).isEqualTo("After look out");
    assertThat(log.get(4)).isEqualTo("--- Checkpoint");
    assertThat(log.get(5)).isEqualTo("Look out window");
  }
}
