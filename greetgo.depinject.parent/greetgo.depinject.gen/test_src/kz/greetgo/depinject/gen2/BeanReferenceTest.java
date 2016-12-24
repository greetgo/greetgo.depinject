package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.gen.errors.IllegalBeanGetterArgumentType;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.List;

import static kz.greetgo.depinject.gen2.TestUtil.getReturnType;
import static org.fest.assertions.api.Assertions.assertThat;

public class BeanReferenceTest {

  class SomeClass {
  }

  interface ForSimple {
    @SuppressWarnings("unused")
    SomeClass method();
  }

  @Test
  public void simple() throws Exception {

    Type type = getReturnType(ForSimple.class, "method");

    BeanReference beanReference = new BeanReference(type, "place 1");

    assertThat(beanReference.targetClass().getName()).isEqualTo(SomeClass.class.getName());
    assertThat(beanReference.isList()).isFalse();
    assertThat(beanReference.place).isEqualTo("place 1");

  }

  
  interface ForList {
    @SuppressWarnings("unused")
    List<SomeClass> method();
  }

  @Test
  public void list() throws Exception {

    Type type = getReturnType(ForList.class, "method");

    BeanReference beanReference = new BeanReference(type, "place 2");

    assertThat(beanReference.targetClass().getName()).isEqualTo(SomeClass.class.getName());
    assertThat(beanReference.isList()).isTrue();
    assertThat(beanReference.place).isEqualTo("place 2");
  }

  interface ForLeftType {
    @SuppressWarnings("unused")
    List<List<SomeClass>> method();
  }

  @Test(expectedExceptions = IllegalBeanGetterArgumentType.class)
  public void leftType() throws Exception {

    Type type = getReturnType(ForLeftType.class, "method");

    new BeanReference(type, "place 3");
  }
}