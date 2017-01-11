package kz.greetgo.depinject.gen2;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class TestUtil {
  public static Type getReturnType(Class<?> aClass, String methodName) {

    for (Method method : aClass.getMethods()) {
      if (methodName.equals(method.getName())) return method.getGenericReturnType();
    }

    throw new IllegalArgumentException("No method with name = " + methodName);
  }

  private static final String MODULE_NAME = "greetgo.depinject.gen";

  public static String buildDir() {

    if (new File(MODULE_NAME).isDirectory()) {
      return MODULE_NAME + "/build";
    }

    return "build";
  }
}
