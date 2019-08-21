package kz.greetgo.depinject.ap.engine;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DepinjectUtil {
  public static Class<?> typeToClass(Type type) {
    if (type instanceof Class) {
      return (Class<?>) type;
    }
    if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      return (Class<?>) pt.getRawType();
    }
    return null;
  }

  public static String toCode(Type type) {
    return toCode0(type).replaceAll("\\$", ".");
  }

  private static String toCode0(Type type) {
    if (type instanceof Class) {
      return ((Class<?>) type).getName();
    }
    return type.toString();
  }

  @SuppressWarnings("unused")
  public static String spaces(int spaces) {
    char[] s = new char[spaces];
    for (int i = 0, n = s.length; i < n; i++) {
      s[i] = ' ';
    }
    return new String(s);
  }

}
