package kz.greetgo.depinject.testng.complex_tests.BeanReplacer;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.complex_tests.BeanReplacer.beans.BeanConfigBeanReplacer;
import kz.greetgo.depinject.testng.complex_tests.BeanReplacer.beans.BeanReplacer_Room;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfigBeanReplacer.class)
public class BeanReplacerTest extends AbstractDepinjectTestNg {
  public static final List<String> log = new ArrayList<>();

  public BeanGetter<BeanReplacer_Room> room;

  @Test
  public void test() throws Exception {

    log.clear();
    log.add("--- Start (replacer)");

    room.get().lookOutWindow();

    log.add("--- Checkpoint (replacer)");

    room.get().lookOutWindowImpl();

    log.forEach(System.out::println);

    assertThat(log.get(0)).isEqualTo("--- Start (replacer)");
    assertThat(log.get(1)).isEqualTo("Before look out");
    assertThat(log.get(2)).isEqualTo("Look out window");
    assertThat(log.get(3)).isEqualTo("After look out");
    assertThat(log.get(4)).isEqualTo("--- Checkpoint (replacer)");
    assertThat(log.get(5)).isEqualTo("Look out window");
  }
}
