package kz.greetgo.depinject.testng.complex_tests.MultipleLink.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

import java.util.List;
import java.util.stream.Collectors;

@Bean
public class MultipleLinkRoom {

  public BeanGetter<List<MultipleLinkWindow>> windowList;

  public List<String> getAllWindowNames() {
    return windowList.get().stream()
      .map(MultipleLinkWindow::name)
      .sorted()
      .collect(Collectors.toList());
  }

}
