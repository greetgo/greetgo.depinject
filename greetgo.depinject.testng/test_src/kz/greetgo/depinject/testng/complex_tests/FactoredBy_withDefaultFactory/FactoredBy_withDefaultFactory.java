package kz.greetgo.depinject.testng.complex_tests.FactoredBy_withDefaultFactory;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.complex_tests.FactoredBy_withDefaultFactory.beans.BeanConfigFactoredByWithDefaultFactory;
import kz.greetgo.depinject.testng.complex_tests.FactoredBy_withDefaultFactory.beans.Room;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@ContainerConfig(BeanConfigFactoredByWithDefaultFactory.class)
public class FactoredBy_withDefaultFactory extends AbstractDepinjectTestNg {

  public static final List<String> log = new ArrayList<>();

  public BeanGetter<Room> room;

  @Test
  public void test() throws Exception {

    log.clear();
    log.add("Start");

    room.get().typeInComputer();

    log.forEach(System.out::println);
    
  }
}
