package kz.greetgo.depinject.gen;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DepinjectUtil {
  public static Class<?> typeToClass(Type type) {
    if (type instanceof Class) return (Class<?>) type;
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
    if (type instanceof Class) return ((Class<?>) type).getName();
    return type.toString();
  }
}
