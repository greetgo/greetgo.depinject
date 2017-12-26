package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.gen.errors.BeanContainerMethodCannotContainAnyArguments;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class ContextTest {

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
  public void extractBeanContainerMethodList_BeanContainerMethodCannotContainAnyArguments() throws Exception {
    Context context = new Context();
    //
    //
    context.extractBeanContainerMethodList(HasMethodWithArguments.class);
    //
    //
  }

  @SuppressWarnings("unused")
  interface GoodBeanContainer {

    List<Bean2> method2();

    Bean1 method1();

  }

  @Test
  public void extractBeanContainerMethodList() throws Exception {
    Context context = new Context();
    //
    //
    List<BeanContainerMethod> list = context.extractBeanContainerMethodList(GoodBeanContainer.class);
    //
    //

    assertThat(list).hasSize(2);

    assertThat(list.get(0).method.getName()).isEqualTo("method1");
    assertThat(list.get(1).method.getName()).isEqualTo("method2");

    assertThat(list.get(0).beanReference.sourceClass.getName()).isEqualTo(Bean1.class.getName());
    assertThat(list.get(1).beanReference.sourceClass.getName()).isEqualTo(Bean2.class.getName());

  }
}