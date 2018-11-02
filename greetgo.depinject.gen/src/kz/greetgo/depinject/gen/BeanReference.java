package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.gen.errors.IllegalBeanGetterArgumentType;
import kz.greetgo.depinject.gen.errors.LeftException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BeanReference {

  public final String place;
  private final Context context;

  public BeanReference(Context context, Type target, String place) {
    if (place == null) {
      throw new NullPointerException("place == null");
    }

    this.context = context;
    this.place = place;

    if (target instanceof Class) {
      sourceClass = (Class<?>) target;
      isList = false;
      return;
    }

    if (target instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) target;

      if (parameterizedType.getRawType() == List.class) {
        Type listArg = parameterizedType.getActualTypeArguments()[0];

        if (listArg instanceof Class) {
          sourceClass = (Class<?>) listArg;
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

  public final Class<?> sourceClass;
  public final boolean isList;

  @SuppressWarnings("unused")
  public String targetClassCode() {
    return isList ? Utils.codeName(List.class) + '<' + Utils.codeName(sourceClass) + '>' : Utils.codeName(sourceClass);
  }

  public final List<GetterCreation> getterCreations = new ArrayList<>();

  private String compareStr = null;

  public String compareStr() {
    if (compareStr == null) {
      compareStr = (isList ? "A_" : "B_") + sourceClass.getName();
    }
    return compareStr;
  }

  @Override
  @SuppressWarnings("RedundantIfStatement")
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    BeanReference that = (BeanReference) o;

    if (isList != that.isList) {
      return false;
    }

    if (!getterCreations.equals(that.getterCreations)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = (isList ? 1 : 0);
    result = 31 * result + getterCreations.hashCode();
    return result;
  }

  private boolean wasFillTargetCreations = false;

  public void fillTargetCreationsFrom(List<BeanCreation> candidates) {
    if (wasFillTargetCreations) {
      return;
    }
    wasFillTargetCreations = true;

    for (BeanCreation candidate : candidates) {
      if (sourceClass.isAssignableFrom(candidate.beanClass)) {

        getterCreations.add(new GetterCreation(sourceClass, candidate));
      }
    }

    getterCreations.sort(Comparator.comparing(o -> o.beanCreation.beanClass.getName()));
  }

  public boolean use = false;

  public void markToUse() {
    if (use) {
      return;
    }
    use = true;
    getterCreations.forEach(GetterCreation::markToUse);
  }

  public String firstBeanToString() {
    if (getterCreations.size() == 0) {
      return "NO_BEAN";
    }
    return getterCreations.get(0).toString();
  }

  @Override
  public String toString() {
    return (isList ? "[" : "") + Utils.asStr(sourceClass) + (isList ? "]" : "")
        + " -> " + getterCreations.size() + '['
        + getterCreations.stream()
        .map(GetterCreation::toString)
        .collect(Collectors.joining(", "))
        + ']';
  }

  public void checkConnectivity() {
    if (isList) {
      return;
    }

    if (getterCreations.size() == 0) {
      throw context.newNoCandidates(this);
    }

    if (getterCreations.size() > 1) {
      throw context.newManyCandidates(this);
    }
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

  private boolean wasUsePreparations = false;

  public void usePreparations(List<BeanCreation> allPreparations) {
    if (wasUsePreparations) { return; }
    wasUsePreparations = true;
    getterCreations.forEach(tc -> tc.usePreparations(allPreparations));
  }

  private boolean wasUseReplacers = false;

  public void useReplacers(List<BeanCreation> allReplacers) {
    if (wasUseReplacers) {
      return;
    }
    wasUseReplacers = true;
    getterCreations.forEach(tc -> tc.useReplacers(allReplacers));
  }


  public boolean needGetter() {
    return isList;
  }

  public int varIndex = 0;

  public String getterVarName() {
    return needGetter()
        ? "getter_ref_" + (isList ? "list_" : "") + sourceClass.getSimpleName() + '_' + varIndex()
        : getterCreations.get(0).getterVarName();
  }

  private String gettingMethodName() {
    if (!needGetter()) {
      throw new LeftException("jhb4jhb5hjb6jn7");
    }
    return "get_ref_" + (isList ? "list_" : "") + sourceClass.getSimpleName() + '_' + varIndex();
  }

  private int varIndex() {
    if (varIndex <= 0) {
      throw new RuntimeException("Left var index = " + varIndex);
    }
    return varIndex;
  }

  public void writeGetter(int tab, Outer outer) {
    if (!needGetter()) { return; }
    outer.nl();
    if (isList) {
      writeGetterAsList(tab, outer);
    } else {
      writeGetterMono(tab, outer);
    }
  }

  @SuppressWarnings("unused")
  private void writeGetterMono(int tab, Outer outer) {
    throw new UnsupportedOperationException();
  }

  private void writeGetterAsList(int tab, Outer outer) {
    outer.tab(tab).stn("private final " + Utils.codeName(BeanGetter.class) + "<" + Utils.codeName(List.class)
        + "<" + Utils.codeName(sourceClass) + ">> " + getterVarName() + " = this::" + gettingMethodName() + ";");
    outer.tab(tab).stn("private " + Utils.codeName(List.class)
        + "<" + Utils.codeName(sourceClass) + "> " + gettingMethodName() + "() {");

    final int tab1 = tab + 1;

    outer.tab(tab1).stn(Utils.codeName(List.class) + "<" + Utils.codeName(sourceClass)
        + "> list = new " + Utils.codeName(ArrayList.class) + "<>();");

    for (GetterCreation gc : getterCreations) {
      gc.getterVarName();
      outer.tab(tab1).stn("list.add(" + gc.getterVarName() + ".get());");
    }

    outer.tab(tab1).stn("return list;");

    outer.tab(tab).stn("}");
  }

  public String accessExpression() {
    return "(" + Utils.codeName(BeanGetter.class) + '<' + targetClassCode() + ">)(java.lang.Object)" + getterVarName();
  }

}
