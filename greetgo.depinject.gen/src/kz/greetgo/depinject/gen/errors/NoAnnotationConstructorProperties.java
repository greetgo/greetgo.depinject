package kz.greetgo.depinject.gen.errors;

import kz.greetgo.depinject.gen.Utils;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.stream.Collectors;

public class NoAnnotationConstructorProperties extends RuntimeException {
  public NoAnnotationConstructorProperties(Class<?> beanClass, Constructor<?> constructor) {
    super("\n\tIn " + Utils.asStr(beanClass) + "\n\tof constructor with arguments types:" + (
        Arrays.stream(constructor.getGenericParameterTypes())
            .map(Object::toString)
            .map(s -> "\n\t\t" + s)
            .collect(Collectors.joining())
    ) + "\n" +
        "\nDepinject finds constructor with maximum number of arguments and use it to create bean." +
        "\nThis constructor MUST be marked with annotation ConstructorProperties." +
        "\nPlease mark this constructor with annotation ConstructorProperties." +
        "\n\nIf you are using lombok then you need add the following to lombok.config file:" +
        "\n\n\tlombok.anyConstructor.addConstructorProperties=true" +
        "\n\n");
  }
}
