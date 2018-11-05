package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.gen.errors.IllegalBeanGetterArgumentType;
import org.testng.annotations.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class BeanReferenceTest {

  class SomeClass {}

  interface ForSimple {
    @SuppressWarnings("unused")
    SomeClass method();
  }

  private static BeanReference.Place testPlace(String display) {
    return new BeanReference.Place() {
      @Override
      public BeanReference.PlaceType type() {
        return null;
      }

      @Override
      public String display() {
        return display;
      }

      @Override
      public String qualifier() {
        throw new NotImplementedException();
      }
    };
  }

  @Test
  public void constructor_simple() {

    Type type = TestUtil.getReturnType(ForSimple.class, "method");

    Context context = new Context();
    BeanReference beanReference = context.newBeanReference(type, testPlace("place 1"));

    assertThat(beanReference.sourceClass.getName()).isEqualTo(SomeClass.class.getName());
    assertThat(beanReference.isList).isFalse();
    assertThat(beanReference.place.display()).isEqualTo("place 1");

  }

  interface ForList {
    @SuppressWarnings("unused")
    List<SomeClass> method();
  }

  @Test
  public void constructor_list() {

    Type type = TestUtil.getReturnType(ForList.class, "method");

    Context context = new Context();
    BeanReference beanReference = context.newBeanReference(type, testPlace("place 2"));

    assertThat(beanReference.sourceClass.getName()).isEqualTo(SomeClass.class.getName());
    assertThat(beanReference.isList).isTrue();
    assertThat(beanReference.place.display()).isEqualTo("place 2");
  }

  interface ForLeftType {
    @SuppressWarnings("unused")
    List<List<SomeClass>> method();
  }

  @Test(expectedExceptions = IllegalBeanGetterArgumentType.class)
  public void constructor_leftType() {

    Type type = TestUtil.getReturnType(ForLeftType.class, "method");

    Context context = new Context();
    context.newBeanReference(type, testPlace("place 3"));

  }

  interface A1_RefInterface {}

  public static class A2_BeanClass implements A1_RefInterface {}

  public static class A3_BeanClass extends A2_BeanClass {}

  public static class A4_LeftClass {}

  @Test
  public void fillTargetCreationsFrom() {

    Context context = new Context();
    BeanReference beanReference = context.newBeanReference(A1_RefInterface.class, testPlace(""));

    BeanCreation beanClass = context.newBeanCreationWithConstructor(A2_BeanClass.class, true);
    BeanCreation beanClass3 = context.newBeanCreationWithConstructor(A3_BeanClass.class, true);
    BeanCreation leftClass = context.newBeanCreationWithConstructor(A4_LeftClass.class, true);

    //
    //
    beanReference.fillTargetCreationsFrom(Arrays.asList(beanClass, beanClass3, leftClass));
    //
    //

    assertThat(beanReference.getterCreations).hasSize(2);

    assertThat(beanReference.getterCreations.get(0).beanCreation.beanClass.getName())
        .isEqualTo(A2_BeanClass.class.getName());

    assertThat(beanReference.getterCreations.get(1).beanCreation.beanClass.getName())
        .isEqualTo(A3_BeanClass.class.getName());
  }
}
