package kz.greetgo.depinject.testng.complex_tests.MultipleLink;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.complex_tests.MultipleLink.beans.BeanConfigMultipleLink;
import kz.greetgo.depinject.testng.complex_tests.MultipleLink.beans.MultipleLinkRoom;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfigMultipleLink.class)
public class MultipleLinkTest extends AbstractDepinjectTestNg {

  public BeanGetter<MultipleLinkRoom> room;

  @Test
  public void test() throws Exception {

    List<String> list = room.get().getAllWindowNames();

//    list.forEach(System.out::println);

    assertThat(list).containsExactly("Center", "Left", "Right", "Top");
  }
}
