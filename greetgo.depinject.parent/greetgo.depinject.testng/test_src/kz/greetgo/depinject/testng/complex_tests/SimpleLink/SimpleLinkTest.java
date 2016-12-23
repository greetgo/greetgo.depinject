package kz.greetgo.depinject.testng.complex_tests.SimpleLink;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.complex_tests.SimpleLink.beans.BeanConfigSimpleLinkTest;
import kz.greetgo.depinject.testng.complex_tests.SimpleLink.beans.SimpleLinkRoom;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfigSimpleLinkTest.class)
public class SimpleLinkTest extends AbstractDepinjectTestNg {

  public BeanGetter<SimpleLinkRoom> simpleLinkRoom;

  public static final List<String> log = new ArrayList<>();

  @Test
  public void test() throws Exception {

    log.clear();
    log.add("--- Start");

    simpleLinkRoom.get().takeJacketOff();
    simpleLinkRoom.get().takeJacketOff();

    log.add("--- checkpoint 1");

    simpleLinkRoom.get().lookOutTheWindow();
    simpleLinkRoom.get().lookOutTheWindow();

    log.add("--- checkpoint 2");

    simpleLinkRoom.get().turnComputerOn();
    simpleLinkRoom.get().turnComputerOn();

    //log.forEach(System.out::println);

    assertThat(log.get(0)).isEqualTo("--- Start");
    assertThat(log.get(1)).isEqualTo("Load class SimpleLinkRoom");
    assertThat(log.get(2)).isEqualTo("Create instance of SimpleLinkRoom");
    assertThat(log.get(3)).isEqualTo("Take off jacket");
    assertThat(log.get(4)).isEqualTo("Take off jacket");
    assertThat(log.get(5)).isEqualTo("--- checkpoint 1");
    assertThat(log.get(6)).isEqualTo("Loading class SimpleLinkWindow");
    assertThat(log.get(7)).isEqualTo("Creating class SimpleLinkWindow");
    assertThat(log.get(8)).isEqualTo("Looking out window");
    assertThat(log.get(9)).isEqualTo("Looking out window");
    assertThat(log.get(10)).isEqualTo("--- checkpoint 2");
    assertThat(log.get(11)).isEqualTo("Load class SimpleLinkComputer");
    assertThat(log.get(12)).isEqualTo("Create class SimpleLinkComputer");
    assertThat(log.get(13)).isEqualTo("Turn computer on");
    assertThat(log.get(14)).isEqualTo("Create class SimpleLinkComputer");
    assertThat(log.get(15)).isEqualTo("Turn computer on");
  }
}
