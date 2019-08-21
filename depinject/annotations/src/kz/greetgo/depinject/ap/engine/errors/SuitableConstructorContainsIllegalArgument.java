package kz.greetgo.depinject.ap.engine.errors;

import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.ann.HideFromDepinject;

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
        + "\n\tIf constructor has arguments, it MUST be marked with annotation @ConstructorProperties({...})."
        + "\n\tAll arguments of this constructor MUST have type " + bg + " (with generics in)"
        + "\n"
        + "\n\tNow depinject found constructor without this type in bean " + beanClass
        + "\n\tYou can do the following:"
        + "\n\t\t- Wrap this argument in " + bg + " : the position of argument is " + (argIndex + 1) + " (the first is 1)"
        + "\n\t\t- Mark the constructor with annotation @" + hfd
        + ", if you do not want depinject to use this constructor."
        + "\n\n\tDepinject uses constructor: " + asStr(constructor)
        + "\n"
        ;
  }

  private static String asStr(Constructor<?> constructor) {
    StringBuilder ret = new StringBuilder(constructor.getName() + "(...)");
    int index = 1;
    for (Type type : constructor.getGenericParameterTypes()) {
      ret.append("\n\t\ttype of argument ").append(index++).append(" : ").append(type);
    }
    return ret.toString();
  }
}
