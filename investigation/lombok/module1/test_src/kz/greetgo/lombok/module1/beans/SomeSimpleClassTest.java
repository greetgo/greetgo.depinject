package kz.greetgo.lombok.module1.beans;

import org.testng.annotations.Test;

import java.beans.ConstructorProperties;
import java.lang.reflect.Constructor;

import static org.fest.assertions.api.Assertions.assertThat;

public class SomeSimpleClassTest {
  @Test
  public void checkConstructorPropertiesExists() {

    Constructor<?>[] constructors = SomeSimpleClass.class.getConstructors();

    assertThat(constructors).hasSize(1);

    ConstructorProperties constructorProperties = constructors[0].getAnnotation(ConstructorProperties.class);

    assertThat(constructorProperties).isNotNull();
    assertThat(constructorProperties.value()[0]).isEqualTo("field1");
    assertThat(constructorProperties.value()[1]).isEqualTo("field2");
  }
}