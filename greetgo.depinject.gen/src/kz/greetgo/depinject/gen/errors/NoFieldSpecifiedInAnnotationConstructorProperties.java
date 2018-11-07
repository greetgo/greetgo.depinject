package kz.greetgo.depinject.gen.errors;

import kz.greetgo.depinject.gen.Utils;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.stream.Collectors;

public class NoFieldSpecifiedInAnnotationConstructorProperties extends RuntimeException {
  public NoFieldSpecifiedInAnnotationConstructorProperties(Class<?> beanClass,
                                                           String fieldNameFromAnnotation,
                                                           Constructor<?> constructor) {
    super("\n\tAnnotation @ConstructorProperties specified field '" + fieldNameFromAnnotation + "'" +
        "\n\tBUT this field not found in " + Utils.asStr(beanClass)
        + "\n\nConstructor contains field with types:\n" + (
        Arrays.stream(constructor.getGenericParameterTypes())
            .map(Object::toString)
            .map(s -> "\t" + s + "\n")
            .collect(Collectors.joining())
    ));
  }
}
