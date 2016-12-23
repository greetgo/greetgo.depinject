package kz.greetgo.depinject.gen2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {
  public static <T extends Annotation> T getAnnotation(Method method, Class<T> annotation) {
    while (true) {
      T ann = method.getAnnotation(annotation);
      if (ann != null) return ann;

      Class<?> aClass = method.getDeclaringClass();
      if (aClass == Object.class) return null;

      Class<?> superclass = aClass.getSuperclass();
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

    if (source == null) return null;

    {
      T ann = source.getAnnotation(annotation);
      if (ann != null) return ann;
    }

    {
      if (cache.contains(source)) return null;
      cache.add(source);
    }

    for (Class<?> aClass : source.getInterfaces()) {
      T ann = getAnnotationInner(aClass, annotation, cache);
      if (ann != null) return ann;
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

    if (source == null) return;

    {
      T ann = source.getAnnotation(annotation);
      if (ann != null) {
        accumulator.add(ann);
      }
    }

    {
      if (cache.contains(source)) return;
      cache.add(source);
    }

    for (Class<?> aClass : source.getInterfaces()) {
      putAllAnnotations(aClass, annotation, accumulator, cache);
    }

    putAllAnnotations(source.getSuperclass(), annotation, accumulator, cache);
  }
}
