package kz.greetgo.depinject.gen.errors;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.HideFromDepinject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

public class SuitableConstructorContainsIllegalArgument extends RuntimeException {
  public SuitableConstructorContainsIllegalArgument(int argIndex,
                                                    Constructor<?> constructor,
                                                    Class<?> beanClass) {
    super(message(argIndex, constructor, beanClass));
  }

  private static String message(int argIndex, Constructor<?> constructor, Class<?> beanClass) {
    String bg = BeanGetter.class.getSimpleName();
    String hfd = HideFromDepinject.class.getSimpleName();
    return "for " + beanClass
        + "\n\tDepinject finds constructor with maximum number of arguments and use it to create bean."
        + "\n\tAll arguments of this constructor MUST have type " + bg + " (with generics in)"
        + "\n"
        + "\n\tNow depinject found constructor without this type in bean " + beanClass
        + "\n\tPlease, wrap this argument in " + bg + " : the position of argument is " + (argIndex + 1) + " (the first is 1)"
        + "\n\n\tIf you do not want depinject used this constructor, mark the constructor with annotation @" + hfd
        + "\n\n\tThe constructor: " + asStr(constructor)
        + "\n"
        ;
  }

  private static String asStr(Constructor<?> constructor) {
    StringBuilder ret = new StringBuilder(constructor.getName() + "(...)");
    int index = 1;
    for (Type type : constructor.getGenericParameterTypes()) {
      ret.append("\n\t\targument № ").append(index++).append(" type : ").append(type);
    }
    return ret.toString();
  }
}
