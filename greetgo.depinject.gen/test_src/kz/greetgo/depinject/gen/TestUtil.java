package kz.greetgo.depinject.gen;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class TestUtil {
  public static Type getReturnType(Class<?> aClass, String methodName) {

    for (Method method : aClass.getMethods()) {
      if (methodName.equals(method.getName())) { return method.getGenericReturnType(); }
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

  public static String extractStackTraceToStr(Throwable error) {
    try {
      ByteArrayOutputStream bOut = new ByteArrayOutputStream();
      PrintStream outStream = new PrintStream(bOut, false, "UTF-8");
      error.printStackTrace(outStream);
      outStream.flush();
      return bOut.toString("UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
