package kz.greetgo.depinject.gen.errors;

import kz.greetgo.depinject.gen.Utils;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MismatchConstructorPropertiesCount extends RuntimeException {
  public MismatchConstructorPropertiesCount(Class<?> beanClass,
                                            int countInAnnotation,
                                            Constructor<?> constructor,
                                            int countInConstructor) {
    super("" +
        "\n\tAnnotation ConstructorProperties contains " + countInAnnotation + " elements" +
        "\n\tBUT constructor contains " + countInConstructor + " arguments." +
        "\n\tConstructing class: " + Utils.asStr(beanClass) +
        "\n\nConstructor argument types:\n" + (

        Arrays.stream(constructor.getGenericParameterTypes())
            .map(Object::toString)
            .map(s -> "\t" + s + "\n")
            .collect(Collectors.joining())
    ));

  }
}
