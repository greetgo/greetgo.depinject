package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ContextTest3 {

  class Bean1 {}

  class Bean2 {}

  class WithDefaultConstructor {
    public BeanGetter<Bean1> bean1;
    public BeanGetter<Bean2> bean2;
  }

  @Test
  public void newBeanCreationWithConstructor_defaultConstructor() {
    Context context = new Context();

    Class<?> beanClass = WithDefaultConstructor.class;

    boolean singleton = RND.bool();

    //
    //
    BeanCreation beanCreation = context.newBeanCreationWithConstructor(beanClass, singleton);
    //
    //

    assertThat(beanCreation).isInstanceOf(BeanCreationWithDefaultConstructor.class);

    assertThat(beanCreation.beanClass.getName()).isEqualTo(beanClass.getName());
    assertThat(beanCreation.singleton).isEqualTo(singleton);
  }

  class OneConstructor {
    private final BeanGetter<Bean1> bean1;
    private final BeanGetter<Bean2> bean2;

    public OneConstructor(BeanGetter<Bean1> bean1, BeanGetter<Bean2> bean2) {
      this.bean1 = bean1;
      this.bean2 = bean2;
    }

    @SuppressWarnings("unused")
    public void show() {
      System.out.println("bean1 = " + bean1);
      System.out.println("bean2 = " + bean2);
    }
  }

  @Test
  public void newBeanCreationWithConstructor_constructorWithArguments() {
    Context context = new Context();

    Class<?> beanClass = OneConstructor.class;

    boolean singleton = RND.bool();

    //
    //
    BeanCreation beanCreation = context.newBeanCreationWithConstructor(beanClass, singleton);
    //
    //

    // TODO: 17.10.18 Доделать этот тест
    assertThat(beanCreation).isInstanceOf(BeanCreationWithDefaultConstructor.class);

  }

  // TODO: 17.10.18 здесь нужны другие тесты, которые проверяют различные варианты конструкторов и бингеттеров
}
