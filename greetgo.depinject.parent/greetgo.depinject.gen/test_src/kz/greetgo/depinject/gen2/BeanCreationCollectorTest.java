package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.errors.NoBeanContainer;
import kz.greetgo.depinject.gen.errors.NoInclude;
import kz.greetgo.depinject.gen2.test_beans001.BeanConfig001;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class BeanCreationCollectorTest {

  public interface BeanContainerWithoutNoBeanContainer {
  }

  @Test(expectedExceptions = NoBeanContainer.class)
  public void collect_NoBeanContainer() throws Exception {
    //
    //
    BeanCreationCollector.collect(BeanContainerWithoutNoBeanContainer.class);
    //
    //
  }

  public interface BeanContainerWithoutInclude extends BeanContainer {
  }

  @Test(expectedExceptions = NoInclude.class)
  public void collect_NoInclude() throws Exception {
    //
    //
    BeanCreationCollector.collect(BeanContainerWithoutInclude.class);
    //
    //
  }

  @Include(BeanConfig001.class)
  public interface HasBeanWithDefaultConstructor extends BeanContainer {
  }

  private static Map<String, BeanCreation> toMapSimple(List<BeanCreation> list) {
    Map<String, BeanCreation> ret = new HashMap<>();
    list.forEach(bc -> ret.put(bc.beanClass.getSimpleName(), bc));
    return ret;
  }

  @Test
  public void collect_BeanWithDefaultConstructor() throws Exception {
    //
    //
    List<BeanCreation> list = BeanCreationCollector.collect(HasBeanWithDefaultConstructor.class);
    //
    //

    assertThat(list).hasSize(2);

    Map<String, BeanCreation> map = toMapSimple(list);

    assertThat(map.get("BeanWithDefaultConstructor1").singleton).isTrue();
    assertThat(map.get("BeanWithDefaultConstructor2").singleton).isFalse();

  }

}