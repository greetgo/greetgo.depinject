package kz.greetgo.depinject.testng.complex_tests.UsingBeanFactory;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.complex_tests.UsingBeanFactory.beans.core.UsingBeanFactoryRoom;
import kz.greetgo.depinject.testng.complex_tests.UsingBeanFactory.util.BeanConfigUsingSmallBeanFactory;
import org.testng.annotations.Test;

import static kz.greetgo.depinject.testng.complex_tests.UsingBeanFactory.util.UsingBeanFactory.log;
import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfigUsingSmallBeanFactory.class)
public class UsingSmallBeanFactoryTest extends AbstractDepinjectTestNg {
  public BeanGetter<UsingBeanFactoryRoom> room;

  @Test
  public void test() throws Exception {

    log.clear();
    log.add("--- Start");

    room.get().takeOffJacket();

    room.get().lookOutWindow();
    room.get().lookOutWindow();

    room.get().turnComputerOn();
    room.get().turnComputerOn();

    //log.forEach(System.out::println);

    assertThat(log.get(0)).isEqualTo("--- Start");
    assertThat(log.get(1)).isEqualTo("Take off jacket");
    assertThat(log.get(2)).isEqualTo("Look out window 1");
    assertThat(log.get(3)).isEqualTo("Look out window 1");
    assertThat(log.get(4)).isEqualTo("Turn on computer 1");
    assertThat(log.get(5)).isEqualTo("Turn on computer 2");
  }
}
