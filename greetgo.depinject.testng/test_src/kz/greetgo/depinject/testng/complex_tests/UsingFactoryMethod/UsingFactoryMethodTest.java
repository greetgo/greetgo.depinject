package kz.greetgo.depinject.testng.complex_tests.UsingFactoryMethod;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.complex_tests.UsingFactoryMethod.beans.BeanConfigUsingFactoryMethod;
import kz.greetgo.depinject.testng.complex_tests.UsingFactoryMethod.beans.UsingFactoryMethodRoom;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfigUsingFactoryMethod.class)
public class UsingFactoryMethodTest extends AbstractDepinjectTestNg {

  public static final List<String> log = new ArrayList<>();

  public BeanGetter<UsingFactoryMethodRoom> room;

  @Test
  public void test() throws Exception {

    log.clear();
    log.add("--- Start");

    room.get().takeOffJacket();

    log.add("--- checkpoint 1");

    room.get().lookOutTheWindow();
    room.get().lookOutTheWindow();

    log.add("--- checkpoint 2");

    room.get().turnOnComputer();
    room.get().turnOnComputer();

    //log.forEach(System.out::println);

    assertThat(log.get(0)).isEqualTo("--- Start");
    assertThat(log.get(1)).isEqualTo("Take off jacket");
    assertThat(log.get(2)).isEqualTo("--- checkpoint 1");
    assertThat(log.get(3)).isEqualTo("Create class FactoryLinkWindow");
    assertThat(log.get(4)).isEqualTo("Look out window at number 1");
    assertThat(log.get(5)).isEqualTo("Look out window at number 1");
    assertThat(log.get(6)).isEqualTo("--- checkpoint 2");
    assertThat(log.get(7)).isEqualTo("Create class FactoryLinkComputer");
    assertThat(log.get(8)).isEqualTo("Turn on computer 1");
    assertThat(log.get(9)).isEqualTo("Create class FactoryLinkComputer");
    assertThat(log.get(10)).isEqualTo("Turn on computer 2");
  }
}
