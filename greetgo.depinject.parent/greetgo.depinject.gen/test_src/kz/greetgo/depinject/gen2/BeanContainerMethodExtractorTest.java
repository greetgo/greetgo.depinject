package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.gen.errors.BeanContainerMethodCannotContainAnyArguments;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class BeanContainerMethodExtractorTest {

  class Bean1 {
  }

  class Bean2 {
  }

  @SuppressWarnings("unused")
  interface HasMethodWithArguments {

    Bean1 methodWithArguments(int x);

    Bean2 goodMethod();

  }

  @Test(expectedExceptions = BeanContainerMethodCannotContainAnyArguments.class)
  public void extract_BeanContainerMethodCannotContainAnyArguments() throws Exception {
    //
    //
    BeanContainerMethodExtractor.extract(HasMethodWithArguments.class);
    //
    //
  }

  @SuppressWarnings("unused")
  interface GoodBeanContainer {

    List<Bean2> method2();

    Bean1 method1();

  }

  @Test
  public void extract() throws Exception {
    //
    //
    List<BeanContainerMethod> list = BeanContainerMethodExtractor.extract(GoodBeanContainer.class);
    //
    //

    assertThat(list).hasSize(2);

    assertThat(list.get(0).method.getName()).isEqualTo("method1");
    assertThat(list.get(1).method.getName()).isEqualTo("method2");

    assertThat(list.get(0).beanReference.targetClass().getName()).isEqualTo(Bean1.class.getName());
    assertThat(list.get(1).beanReference.targetClass().getName()).isEqualTo(Bean2.class.getName());

  }
}