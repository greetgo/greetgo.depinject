package kz.greetgo.depinject.mvc;

import java.lang.reflect.Type;

public class MvcUtil {
  public static Object convertStrsToType(String[] strs, Type type) {

    if (type instanceof Class) {
      Class<?> typeClass = (Class<?>) type;

      if (typeClass.equals(String.class)) {

        if (strs == null) return null;
        if (strs.length == 0) return null;
        return strs[0];

      }

    }

    throw new IllegalArgumentException("Cannot convert strings to " + type);
  }
}
