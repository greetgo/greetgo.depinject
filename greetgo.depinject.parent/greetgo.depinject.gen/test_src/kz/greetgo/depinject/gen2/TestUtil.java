package kz.greetgo.depinject.gen2;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class TestUtil {
  public static Type getReturnType(Class<?> aClass, String methodName) {

    for (Method method : aClass.getMethods()) {
      if (methodName.equals(method.getName())) return method.getGenericReturnType();
    }

    throw new IllegalArgumentException("No method with name = " + methodName);
  }
}
