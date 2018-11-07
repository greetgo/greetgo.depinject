package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.HideFromDepinject;
import kz.greetgo.depinject.gen.errors.MismatchConstructorPropertiesCount;
import kz.greetgo.depinject.gen.errors.NoAnnotationConstructorProperties;
import kz.greetgo.depinject.gen.errors.NoConstructorsToCreateBean;
import kz.greetgo.depinject.gen.errors.SuitableConstructorContainsIllegalArgument;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import java.beans.ConstructorProperties;

import static kz.greetgo.depinject.gen.TestUtil.beanAnn;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

public class Context_newBeanCreationWithConstructor_Test {

  class Bean1 {}

  class Bean2 {}

  class Bean3 {}

  public static class WithDefaultConstructor {
    public BeanGetter<Bean1> bean1;
    public BeanGetter<Bean2> bean2;
  }

  @Test
  public void default_constructor_with_fields() {
    Context context = new Context();

    Class<?> beanClass = WithDefaultConstructor.class;

    boolean singleton = RND.bool();

    //
    //
    BeanCreation beanCreation = context.newBeanCreationWithConstructor(beanClass, beanAnn(singleton));
    //
    //

    assertThat(beanCreation).isInstanceOf(BeanCreationWithConstructor.class);

    assertThat(beanCreation.beanClass.getName()).isEqualTo(beanClass.getName());
    assertThat(beanCreation.isSingleton()).isEqualTo(singleton);

    BeanCreationWithConstructor bc = (BeanCreationWithConstructor) beanCreation;
    assertThat(bc.argList).isEmpty();
  }

  public static class OneConstructor {
    private final BeanGetter<Bean1> bean1;
    private final BeanGetter<Bean2> bean2;

    @ConstructorProperties({"bean1", "bean2"})
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
  public void oneConstructorWithArguments() {
    Context context = new Context();

    Class<?> beanClass = OneConstructor.class;

    boolean singleton = RND.bool();

    //
    //
    BeanCreation beanCreation = context.newBeanCreationWithConstructor(beanClass, beanAnn(singleton));
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

    @ConstructorProperties({"bean1"})
    public ManyConstructors(BeanGetter<Bean1> bean1) {
      this.bean1 = bean1;
      this.bean2 = null;
      this.bean3 = null;
    }

    @ConstructorProperties({"bean2", "bean1"})
    public ManyConstructors(BeanGetter<Bean2> bean2, BeanGetter<Bean1> bean1) {
      this.bean1 = bean1;
      this.bean2 = bean2;
      this.bean3 = null;
    }

    @ConstructorProperties({"bean3", "bean1", "bean2"})
    public ManyConstructors(BeanGetter<Bean3> bean3, BeanGetter<Bean1> bean1, BeanGetter<Bean2> bean2) {
      this.bean1 = bean1;
      this.bean2 = bean2;
      this.bean3 = bean3;
    }
  }

  @Test
  public void twoConstructors() {
    Context context = new Context();

    Class<?> beanClass = ManyConstructors.class;

    boolean singleton = RND.bool();

    //
    //
    BeanCreation beanCreation = context.newBeanCreationWithConstructor(beanClass, beanAnn(singleton));
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
  public void noConstructors() {
    Context context = new Context();

    Class<?> beanClass = NoConstructors.class;

    boolean singleton = RND.bool();

    NoConstructorsToCreateBean error = null;

    try {
      //
      //
      context.newBeanCreationWithConstructor(beanClass, beanAnn(singleton));
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

    @ConstructorProperties({"bean1"})
    public ManyConstructorsButSuitableIsLeft(BeanGetter<Bean1> bean1) {
      this.bean1 = bean1;
      this.bean2 = null;
      this.bean3 = null;
      this.notBeanGetter = null;
    }

    @ConstructorProperties({"bean2", "bean1"})
    public ManyConstructorsButSuitableIsLeft(BeanGetter<Bean2> bean2, BeanGetter<Bean1> bean1) {
      this.bean1 = bean1;
      this.bean2 = bean2;
      this.bean3 = null;
      this.notBeanGetter = null;
    }

    private final SomeClass notBeanGetter;

    @ConstructorProperties({"bean3", "bean1", "bean2", "notBeanGetter"})
    public ManyConstructorsButSuitableIsLeft(BeanGetter<Bean3> bean3,
                                             BeanGetter<Bean1> bean1,
                                             BeanGetter<Bean2> bean2,
                                             SomeClass notBeanGetter
    ) {
      this.bean1 = bean1;
      this.bean2 = bean2;
      this.bean3 = bean3;
      this.notBeanGetter = notBeanGetter;
    }
  }

  @Test
  public void many_constructors_but_suitable_is_wrong() {
    Context context = new Context();

    Class<?> beanClass = ManyConstructorsButSuitableIsLeft.class;

    boolean singleton = RND.bool();

    SuitableConstructorContainsIllegalArgument error = null;

    try {
      //
      //
      context.newBeanCreationWithConstructor(beanClass, beanAnn(singleton));
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
    private final BeanGetter<Bean3> bean3;

    @ConstructorProperties({"bean1"})
    public CheckWorkingOfAnnotation_HideFromDepinject(BeanGetter<Bean1> bean1) {
      this.bean1 = bean1;
      this.bean2 = null;
      bean3 = null;
      notBeanGetter = null;
    }

    @ConstructorProperties({"bean2", "bean1"})
    public CheckWorkingOfAnnotation_HideFromDepinject(BeanGetter<Bean2> bean2, BeanGetter<Bean1> bean1) {
      this.bean1 = bean1;
      this.bean2 = bean2;
      bean3 = null;
      notBeanGetter = null;
    }

    private final SomeClass notBeanGetter;

    @HideFromDepinject
    @ConstructorProperties({"bean3", "bean1", "bean2", "notBeanGetter"})
    public CheckWorkingOfAnnotation_HideFromDepinject(BeanGetter<Bean3> bean3,
                                                      BeanGetter<Bean1> bean1,
                                                      BeanGetter<Bean2> bean2,
                                                      SomeClass notBeanGetter
    ) {
      this.bean1 = bean1;
      this.bean2 = bean2;
      this.bean3 = bean3;
      this.notBeanGetter = notBeanGetter;
    }
  }

  @Test
  public void check_working_of_annotation_HideFromDepinject() {
    Context context = new Context();

    Class<?> beanClass = CheckWorkingOfAnnotation_HideFromDepinject.class;

    boolean singleton = RND.bool();

    //
    //
    context.newBeanCreationWithConstructor(beanClass, beanAnn(singleton));
    //
    //

    //No ERROR - good: annotation @HideFromDepinject is working good
  }

  public static class BeanWithDefaultConstructor {
    //No annotation @ConstructorProperties
    public BeanWithDefaultConstructor() {}
  }

  @Test
  public void constructor_without_arguments_may_absent_annotation_ConstructorProperties() {
    Context context = new Context();

    Class<?> beanClass = BeanWithDefaultConstructor.class;

    boolean singleton = RND.bool();

    //
    //
    context.newBeanCreationWithConstructor(beanClass, beanAnn(singleton));
    //
    //
  }

  @SuppressWarnings("unused")
  public static class BeanWithConstructorWithoutAnnotationConstructorProperties {
    private final BeanGetter<Bean1> bean1;
    private final BeanGetter<Bean2> bean2;

    //No annotation @ConstructorProperties
    public BeanWithConstructorWithoutAnnotationConstructorProperties(BeanGetter<Bean1> bean1,
                                                                     BeanGetter<Bean2> bean2) {
      this.bean1 = bean1;
      this.bean2 = bean2;
    }
  }

  @Test
  public void constructor_with_arguments_and_without_ann_ConstructorProperties_throws_error() {
    Context context = new Context();

    Class<?> beanClass = BeanWithConstructorWithoutAnnotationConstructorProperties.class;

    boolean singleton = RND.bool();

    NoAnnotationConstructorProperties error = null;

    try {
      //
      //
      context.newBeanCreationWithConstructor(beanClass, beanAnn(singleton));
      //
      //

      fail("Must be error: NoAnnotationConstructorProperties");
    } catch (NoAnnotationConstructorProperties e) {
      error = e;
    }

    assertThat(error).isNotNull();

    String st = TestUtil.extractStackTraceToStr(error);
    System.out.println(st);

    String cp = ConstructorProperties.class.getSimpleName();

    assertThat(st).contains("Depinject finds constructor with maximum number of arguments and use it to create bean.");
    assertThat(st).contains("This constructor MUST be marked with annotation " + cp + ".");
    assertThat(st).contains("Please mark this constructor with annotation " + cp + ".");
  }

  @SuppressWarnings("unused")
  public static class BeanWithMismatchConstructorPropertiesCount {
    private final BeanGetter<Bean1> bean1;
    private final BeanGetter<Bean2> bean2;

    @ConstructorProperties({"bean1", "bean2", "wow"})
    public BeanWithMismatchConstructorPropertiesCount(BeanGetter<Bean1> bean1,
                                                      BeanGetter<Bean2> bean2) {
      this.bean1 = bean1;
      this.bean2 = bean2;
    }
  }

  @Test
  public void mismatch_constructor_properties_count() {
    Context context = new Context();

    Class<?> beanClass = BeanWithMismatchConstructorPropertiesCount.class;

    boolean singleton = RND.bool();

    MismatchConstructorPropertiesCount error = null;

    try {
      //
      //
      context.newBeanCreationWithConstructor(beanClass, beanAnn(singleton));
      //
      //

      fail("Must be error: MismatchConstructorPropertiesCount");
    } catch (MismatchConstructorPropertiesCount e) {
      error = e;
    }

    assertThat(error).isNotNull();

    String st = TestUtil.extractStackTraceToStr(error);
    System.out.println(st);

    assertThat(st).contains("Annotation ConstructorProperties contains 3 elements");
    assertThat(st).contains("constructor contains 2 arguments");
  }
}
