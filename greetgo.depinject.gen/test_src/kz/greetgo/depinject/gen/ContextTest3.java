package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.HideFromDepinject;
import kz.greetgo.depinject.core.SkipInject;
import kz.greetgo.depinject.gen.errors.NoConstructorsToCreateBean;
import kz.greetgo.depinject.gen.errors.NotPublicBeanWithoutConstructor;
import kz.greetgo.depinject.gen.errors.SuitableConstructorContainsIllegalArgument;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

public class ContextTest3 {

  class Bean1 {}

  class Bean2 {}

  class Bean3 {}

  public static class WithDefaultConstructor {
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

    assertThat(beanCreation).isInstanceOf(BeanCreationWithConstructor.class);

    assertThat(beanCreation.beanClass.getName()).isEqualTo(beanClass.getName());
    assertThat(beanCreation.singleton).isEqualTo(singleton);

    BeanCreationWithConstructor bc = (BeanCreationWithConstructor) beanCreation;
    assertThat(bc.argList).isEmpty();
  }

  public static class OneConstructor {
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
  public void newBeanCreationWithConstructor_oneConstructorWithArguments() {
    Context context = new Context();

    Class<?> beanClass = OneConstructor.class;

    boolean singleton = RND.bool();

    //
    //
    BeanCreation beanCreation = context.newBeanCreationWithConstructor(beanClass, singleton);
    //
    //

    assertThat(beanCreation).isInstanceOf(BeanCreationWithConstructor.class);

    BeanCreationWithConstructor bc = (BeanCreationWithConstructor) beanCreation;

    assertThat(bc.argList).hasSize(2);
    assertThat(bc.argList.get(0).beanReference().sourceClass.getName()).isEqualTo(Bean1.class.getName());
    assertThat(bc.argList.get(1).beanReference().sourceClass.getName()).isEqualTo(Bean2.class.getName());
  }

  @SuppressWarnings("unused")
  public static class ManyConstructors {
    private final BeanGetter<Bean1> bean1;
    private final BeanGetter<Bean2> bean2;
    private final BeanGetter<Bean3> bean3;

    public ManyConstructors(BeanGetter<Bean1> bean1) {
      this.bean1 = bean1;
      this.bean2 = null;
      this.bean3 = null;
    }

    public ManyConstructors(BeanGetter<Bean2> bean2, BeanGetter<Bean1> bean1) {
      this.bean1 = bean1;
      this.bean2 = bean2;
      this.bean3 = null;
    }

    public ManyConstructors(BeanGetter<Bean3> bean3, BeanGetter<Bean1> bean1, BeanGetter<Bean2> bean2) {
      this.bean1 = bean1;
      this.bean2 = bean2;
      this.bean3 = bean3;
    }
  }

  @Test
  public void newBeanCreationWithConstructor_twoConstructors() {
    Context context = new Context();

    Class<?> beanClass = ManyConstructors.class;

    boolean singleton = RND.bool();

    //
    //
    BeanCreation beanCreation = context.newBeanCreationWithConstructor(beanClass, singleton);
    //
    //

    assertThat(beanCreation).isInstanceOf(BeanCreationWithConstructor.class);

    BeanCreationWithConstructor bc = (BeanCreationWithConstructor) beanCreation;

    assertThat(bc.argList).hasSize(3);
    assertThat(bc.argList.get(0).beanReference().sourceClass.getName()).isEqualTo(Bean3.class.getName());
    assertThat(bc.argList.get(1).beanReference().sourceClass.getName()).isEqualTo(Bean1.class.getName());
    assertThat(bc.argList.get(2).beanReference().sourceClass.getName()).isEqualTo(Bean2.class.getName());
  }

  @SuppressWarnings("unused")
  public static class NoConstructors {
    private NoConstructors() {}
  }

  @Test
  public void newBeanCreationWithConstructor_noConstructors() {
    Context context = new Context();

    Class<?> beanClass = NoConstructors.class;

    boolean singleton = RND.bool();

    NoConstructorsToCreateBean error = null;

    try {
      //
      //
      context.newBeanCreationWithConstructor(beanClass, singleton);
      //
      //

      fail("Must be error: no constructors");
    } catch (NoConstructorsToCreateBean e) {
      error = e;
    }

    assertThat(error).isNotNull();

    String stackTrace = TestUtil.extractStackTraceToStr(error);

//    System.out.println(stackTrace);

    assertThat(stackTrace).contains(NoConstructorsToCreateBean.class.getSimpleName());
    assertThat(stackTrace).contains("for " + NoConstructors.class);
    assertThat(stackTrace).contains("Depinject can access only to public constructors.");
    assertThat(stackTrace).contains("Check public access to constructor you want to use to create bean with depinject.");
  }

  public static class SomeClass {}

  @SuppressWarnings("unused")
  public static class ManyConstructorsButSuitableIsLeft {
    private final BeanGetter<Bean1> bean1;
    private final BeanGetter<Bean2> bean2;
    private final BeanGetter<Bean3> bean3;

    public ManyConstructorsButSuitableIsLeft(BeanGetter<Bean1> bean1) {
      this.bean1 = bean1;
      this.bean2 = null;
      this.bean3 = null;
    }

    public ManyConstructorsButSuitableIsLeft(BeanGetter<Bean2> bean2, BeanGetter<Bean1> bean1) {
      this.bean1 = bean1;
      this.bean2 = bean2;
      this.bean3 = null;
    }

    public ManyConstructorsButSuitableIsLeft(BeanGetter<Bean3> bean3,
                                             BeanGetter<Bean1> bean1,
                                             BeanGetter<Bean2> bean2,
                                             SomeClass notBeanGetter
    ) {
      this.bean1 = bean1;
      this.bean2 = bean2;
      this.bean3 = bean3;
    }
  }

  @Test
  public void newBeanCreationWithConstructor_ManyConstructorsButSuitableIsLeft() {
    Context context = new Context();

    Class<?> beanClass = ManyConstructorsButSuitableIsLeft.class;

    boolean singleton = RND.bool();

    SuitableConstructorContainsIllegalArgument error = null;

    try {
      //
      //
      context.newBeanCreationWithConstructor(beanClass, singleton);
      //
      //

      fail("Must be error: suitable constructor contains not-bean-getter-argument." +
          " It situation must generate understandable error message");
    } catch (SuitableConstructorContainsIllegalArgument e) {
      error = e;
    }

    assertThat(error).isNotNull();

    String st = TestUtil.extractStackTraceToStr(error);

    System.out.println(st);

    assertThat(st).contains(SuitableConstructorContainsIllegalArgument.class.getSimpleName());
    assertThat(st).contains("for " + ManyConstructorsButSuitableIsLeft.class);
    assertThat(st).contains("Depinject finds constructor with maximum number of arguments and use it to create bean.");
    assertThat(st).contains("All arguments of this constructor MUST have type BeanGetter (with generics in)");
    assertThat(st).contains("Now depinject found constructor without this type in bean " + beanClass);
    assertThat(st).contains("- Wrap this argument in BeanGetter");
    assertThat(st).contains("- Mark the constructor with annotation @HideFromDepinject");
  }

  @SuppressWarnings("unused")
  public static class CheckWorkingOfAnnotation_HideFromDepinject {
    private final BeanGetter<Bean1> bean1;
    private final BeanGetter<Bean2> bean2;

    public CheckWorkingOfAnnotation_HideFromDepinject(BeanGetter<Bean1> bean1) {
      this.bean1 = bean1;
      this.bean2 = null;
    }

    public CheckWorkingOfAnnotation_HideFromDepinject(BeanGetter<Bean2> bean2, BeanGetter<Bean1> bean1) {
      this.bean1 = bean1;
      this.bean2 = bean2;
    }

    @HideFromDepinject
    public CheckWorkingOfAnnotation_HideFromDepinject(BeanGetter<Bean3> bean3,
                                                      BeanGetter<Bean1> bean1,
                                                      BeanGetter<Bean2> bean2,
                                                      SomeClass notBeanGetter
    ) {
      this.bean1 = bean1;
      this.bean2 = bean2;
    }
  }

  @Test
  public void newBeanCreationWithConstructor_CheckWorkingOfAnnotation_HideFromDepinject() {
    Context context = new Context();

    Class<?> beanClass = CheckWorkingOfAnnotation_HideFromDepinject.class;

    boolean singleton = RND.bool();

    //
    //
    context.newBeanCreationWithConstructor(beanClass, singleton);
    //
    //

    //No ERROR - good: annotation @HideFromDepinject is working good
  }

}
