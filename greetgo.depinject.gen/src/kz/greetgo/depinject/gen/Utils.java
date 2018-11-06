package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.FactoredBy;
import kz.greetgo.depinject.core.Qualifier;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {
  public static <T extends Annotation> T getAnnotation(Method method, Class<T> annotation) {
    while (true) {
      T ann = method.getAnnotation(annotation);
      if (ann != null) {
        return ann;
      }

      Class<?> aClass = method.getDeclaringClass();
      if (aClass == Object.class) {
        return null;
      }

      Class<?> superclass = aClass.getSuperclass();
      if (superclass == null) {
        return null;
      }

      try {
        method = superclass.getMethod(method.getName(), method.getParameterTypes());
      } catch (NoSuchMethodException e) {
        return null;
      }
    }
  }

  public static <T extends Annotation> T getAnnotation(Class<?> source, Class<T> annotation) {
    return getAnnotationInner(source, annotation, new HashSet<>());
  }

  private static <T extends Annotation> T getAnnotationInner(Class<?> source, Class<T> annotation, Set<Class<?>> cache) {

    if (source == null) {
      return null;
    }

    {
      T ann = source.getAnnotation(annotation);
      if (ann != null) {
        return ann;
      }
    }

    {
      if (cache.contains(source)) {
        return null;
      }
      cache.add(source);
    }

    for (Class<?> aClass : source.getInterfaces()) {
      T ann = getAnnotationInner(aClass, annotation, cache);
      if (ann != null) {
        return ann;
      }
    }

    return getAnnotationInner(source.getSuperclass(), annotation, cache);
  }

  public static <T extends Annotation> List<T> getAllAnnotations(Class<?> source, Class<T> annotation) {
    List<T> ret = new ArrayList<>();
    putAllAnnotations(source, annotation, ret, new HashSet<>());
    return ret;
  }

  private static <T extends Annotation> void putAllAnnotations(
      Class<?> source, Class<T> annotation,
      List<T> accumulator,
      Set<Class<?>> cache
  ) {

    if (source == null) {
      return;
    }

    {
      T ann = source.getAnnotation(annotation);
      if (ann != null) {
        accumulator.add(ann);
      }
    }

    {
      if (cache.contains(source)) {
        return;
      }
      cache.add(source);
    }

    for (Class<?> aClass : source.getInterfaces()) {
      putAllAnnotations(aClass, annotation, accumulator, cache);
    }

    putAllAnnotations(source.getSuperclass(), annotation, accumulator, cache);
  }

  public static boolean isRealClass(Class<?> aClass) {
    boolean isInterface = aClass.isInterface();
    boolean isAbstract = Modifier.isAbstract(aClass.getModifiers());
    return !isInterface && !isAbstract;
  }

  public static String asStr(Class<?> aClass) {
    boolean isInterface = aClass.isInterface();
    if (isInterface) {
      //noinspection SpellCheckingInspection
      return "iface " + aClass.getSimpleName();
    }

    boolean isAbstract = Modifier.isAbstract(aClass.getModifiers());
    if (isAbstract) {
      return "abstract " + aClass.getSimpleName();
    }

    return aClass.getSimpleName();
  }

  public static Class<?> extractRawClass(Type type) {
    if (type instanceof Class) {
      return (Class<?>) type;
    }
    if (type instanceof ParameterizedType) {
      return extractRawClass(((ParameterizedType) type).getRawType());
    }
    throw new IllegalArgumentException("Left type " + type);
  }

  public static String codeName(Class<?> aClass) {
    return aClass.getName().replaceAll("\\$", ".");
  }

  public static String streamToStr(InputStream inputStream) {
    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    copyStreams(inputStream, bOut, 512);
    try {
      return bOut.toString("UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unused")
  public static void copyStreams(InputStream inputStream, OutputStream outputStream) {
    copyStreams(inputStream, outputStream, 4 * 1024);
  }

  public static void copyStreams(InputStream inputStream, OutputStream outputStream, int bufferSize) {
    byte buffer[] = new byte[bufferSize];

    try {
      try {

        while (true) {

          int readCount = inputStream.read(buffer);
          if (readCount < 0) {
            return;
          }

          outputStream.write(buffer, 0, readCount);

        }

      } finally {
        inputStream.close();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String typeAsStr(Type type) {
    if (type instanceof Class) {
      return asStr((Class) type);
    }
    return type.toString();
  }

  public static Qualifier newQualifier(String value, boolean regexp) {
    return new Qualifier() {
      @Override
      public Class<? extends Annotation> annotationType() {
        return getClass();
      }

      @Override
      public String value() {
        return value;
      }

      @Override
      public boolean regexp() {
        return regexp;
      }
    };
  }

  public static Qualifier noneNull(Qualifier qualifier) {
    if (qualifier != null) {
      return qualifier;
    }

    return newQualifier("", false);
  }

  public static Qualifier beanConfigToQualifier(BeanConfig beanConfig) {
    if (beanConfig == null) {
      return noneNull(null);
    }
    return newQualifier(beanConfig.qualifier(), beanConfig.qualifierRegexp());
  }

  public static Qualifier factoredByToQualifier(FactoredBy factoredBy) {
    if (factoredBy == null) {
      return noneNull(null);
    }
    return newQualifier(factoredBy.qualifier(), factoredBy.qualifierRegexp());
  }
}
