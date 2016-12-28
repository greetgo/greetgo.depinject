package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.gen.errors.IllegalBeanGetterArgumentType;
import kz.greetgo.depinject.gen.errors.ManyCandidates;
import kz.greetgo.depinject.gen.errors.NoCandidates;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

  public boolean use = false;

  public void markToUse() {
    if (use) return;
    use = true;
    targetCreations.forEach(BeanCreation::markToUse);
    preparations.forEach(BeanCreation::markToUse);
  }

  public String firstBeanToString() {
    if (targetCreations.size() == 0) return "NO_BEAN";
    return targetCreations.get(0).toString();
  }

  @Override
  public String toString() {
    return (isList ? "[" : "") + Utils.asStr(targetClass) + (isList ? "]" : "") + " -> " + targetCreations.size() + '['
      + targetCreations.stream().map(BeanCreation::toString).collect(Collectors.joining(", ")) + ']';
  }

  public void check() {
    if (isList) return;

    if (targetCreations.size() == 0) throw new NoCandidates(this);

    if (targetCreations.size() > 1) throw new ManyCandidates(this);

  }

  public final List<BeanCreation> preparations = new ArrayList<>();

  public void usePreparations(List<BeanCreation> allPreparations) {
    Class<?> currentClass = targetClass;
    for (BeanCreation preparation : allPreparations) {
      Class<?> pc = preparation.preparingClass;
      if (pc != null && currentClass.isAssignableFrom(pc)) {
        preparations.add(preparation);
        currentClass = pc;
      }
    }
    Collections.reverse(preparations);
  }

  public String toFullString() {
    return toString() + preparationsStr();
  }

  private String preparationsStr() {
    StringBuilder sb = new StringBuilder();
    for (BeanCreation preparation : preparations) {
      sb.append("\n\tPrepared by ").append(preparation);
    }
    return sb.toString();
  }
}
