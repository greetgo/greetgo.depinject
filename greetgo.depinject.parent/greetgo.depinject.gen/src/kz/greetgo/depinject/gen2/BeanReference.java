package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.gen.errors.IllegalBeanGetterArgumentType;
import kz.greetgo.depinject.gen.errors.ManyCandidates;
import kz.greetgo.depinject.gen.errors.NoCandidates;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
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

  public final List<GetterCreation> getterCreations = new ArrayList<>();

  public void fillTargetCreationsFrom(List<BeanCreation> candidates) {

    for (BeanCreation candidate : candidates) {
      if (targetClass.isAssignableFrom(candidate.beanClass)) {
        getterCreations.add(new GetterCreation(targetClass, candidate));
      }
    }

    getterCreations.sort(Comparator.comparing(o -> o.beanCreation.beanClass.getName()));
  }

  public boolean use = false;

  public void markToUse() {
    if (use) return;
    use = true;
    getterCreations.forEach(GetterCreation::markToUse);
  }

  public String firstBeanToString() {
    if (getterCreations.size() == 0) return "NO_BEAN";
    return getterCreations.get(0).toString();
  }

  @Override
  public String toString() {
    return (isList ? "[" : "") + Utils.asStr(targetClass) + (isList ? "]" : "")
      + " -> " + getterCreations.size() + '['
      + getterCreations.stream()
      .map(tc -> tc.beanCreation)
      .map(BeanCreation::toString)
      .collect(Collectors.joining(", "))
      + ']';
  }

  public void check() {
    if (isList) return;

    if (getterCreations.size() == 0) throw new NoCandidates(this);

    if (getterCreations.size() > 1) throw new ManyCandidates(this);

  }

  public String toFullString() {
    return toString() + preparationsStr();
  }

  private String preparationsStr() {
    StringBuilder sb = new StringBuilder();
    for (GetterCreation getterCreation : getterCreations) {
      sb.append(getterCreation.preparationStr());
    }
    return sb.toString();
  }

  public void usePreparations(List<BeanCreation> allPreparations) {
    getterCreations.forEach(tc -> tc.usePreparations(allPreparations));
  }

  public boolean needGetter() {
    if (getterCreations.size() > 1) return true;

    if (getterCreations.get(0).preparations.size() > 0) return true;

    return false;
  }

  public int varIndex = 0;

  public String getBeanGetterVarName() {
    return needGetter()
      ? "getter_ref_" + (isList ? "list_" : "") + targetClass.getSimpleName() + '_' + varIndex()
      : getterCreations.get(0).getBeanGetterVarName();
  }

  private int varIndex() {
    if (varIndex <= 0) throw new RuntimeException("Left var index = " + varIndex);
    return varIndex;
  }
}
