package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.gen.errors.IllegalBeanGetterArgumentType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeanReference {

  public final String place;

  public BeanReference(Type target, String place) {
    if (place == null) throw new NullPointerException("place == null");
    this.place = place;

    if (target instanceof Class) {
      targetClass = (Class<?>) target;
      isList = false;
      return;
    }

    if (target instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) target;

      if (parameterizedType.getRawType() == List.class) {
        Type listArg = parameterizedType.getActualTypeArguments()[0];

        if (listArg instanceof Class) {
          targetClass = (Class<?>) listArg;
          isList = true;
          return;
        }

        throw new IllegalBeanGetterArgumentType("Cannot extract bean class from List: "
          + parameterizedType.toString() + "; " + place);
      }

      throw new IllegalBeanGetterArgumentType("Cannot extract bean class from parameterized type: "
        + parameterizedType.toString() + "; " + place);
    }

    throw new IllegalBeanGetterArgumentType("Cannot extract bean class from type: " + target.toString() + "; " + place);
  }

  public final Class<?> targetClass;
  public final boolean isList;

  public final List<BeanCreation> targetCreations = new ArrayList<>();

  public void fillTargetCreationsFrom(List<BeanCreation> candidates) {

    for (BeanCreation candidate : candidates) {
      if (targetClass.isAssignableFrom(candidate.beanClass)) {
        targetCreations.add(candidate);
      }
    }

    Collections.sort(targetCreations);
  }
}
