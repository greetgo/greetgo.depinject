package kz.greetgo.depinject.ap.engine;

import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.ann.util.AnnProcUtil;
import kz.greetgo.depinject.ann.util.BeanRefData;
import kz.greetgo.depinject.ann.util.Place;
import kz.greetgo.depinject.ann.util.QualifierData;
import kz.greetgo.depinject.ann.util.message.CommonError;

import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BeanReference {

  public final Place place;
  private final Context context;
  public final BeanRefData data;

  public BeanReference(Context context, TypeMirror target, Place place) {
    Objects.requireNonNull(place);

    this.context = context;
    this.place = place;

    data = AnnProcUtil.toBeanRefData(target, place);
  }

  @SuppressWarnings("unused")
  public String targetClassCode() {
    return data.isList
      ? Utils.codeName(List.class) + '<' + AnnProcUtil.toCode(data.typeElement) + '>'
      : AnnProcUtil.toCode(data.typeElement);
  }

  public final List<GetterCreation> getterCreations = new ArrayList<>();

  private String compareStr = null;

  public String compareStr() {
    if (compareStr == null) {
      compareStr = (data.isList ? "A." : "B.") + AnnProcUtil.toCode(data.typeElement);
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

    if (data.isList != that.data.isList) {
      return false;
    }

    if (!getterCreations.equals(that.getterCreations)) {
      return false;
    }

    if (!Objects.equals(place.qualifier().value, place.qualifier().value)) {
      return false;
    }

    if (place.qualifier().regexp != place.qualifier().regexp) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = (data.isList ? 1 : 0);

    result = 31 * result + getterCreations.hashCode();

    QualifierData qualifier = place.qualifier();

    result = 31 * result + qualifier.value.hashCode();
    result = 31 * result + (qualifier.regexp ? 1 : 0);

    return result;
  }

  private boolean wasFillTargetCreations = false;

  public final List<BeanCreation> assignableCandidates = new ArrayList<>();

  //TODO pompei ....
  public void fillTargetCreationsFrom(List<BeanCreation> candidates) {
    if (wasFillTargetCreations) {
      return;
    }
    wasFillTargetCreations = true;

    for (BeanCreation candidate : candidates) {
      if (isReferencingTo(candidate)) {
        getterCreations.add(new GetterCreation(data.typeElement, candidate));
      }

      //context.processingEnv.getTypeUtils().isAssignable()

      if (sourceClass.isAssignableFrom(candidate.beanClass)) {
        assignableCandidates.add(candidate);
      }
    }

    getterCreations.sort(Comparator.comparing(o -> o.beanCreation.beanClass.getName()));
  }

  private boolean isReferencingTo(BeanCreation candidate) {

    if (!sourceClass.isAssignableFrom(candidate.beanClass)) {
      return false;
    }

    if (place.qualifier().value.isEmpty()) {
      return true;
    }

    if (place.qualifier().regexp) {
      return Pattern
        .compile(place.qualifier().value)
        .matcher(candidate.bean.id())
        .matches();
    }

    return place.qualifier().value.equals(candidate.bean.id());
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
    return (data.isList ? "[" : "") + Utils.asStr(data.typeElement) + (data.isList ? "]" : "")
      + " -> " + getterCreations.size() + '['
      + getterCreations.stream()
      .map(GetterCreation::toString)
      .collect(Collectors.joining(", "))
      + ']';
  }

  public void checkConnectivity() {
    if (data.isList) {
      return;
    }

    if (getterCreations.size() == 0) {
      if (assignableCandidates.isEmpty()) {
        throw context.newNoCandidates(this);
      } else {
        throw context.newQualifierNotMatched(this);
      }
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
    if (wasUsePreparations) {
      return;
    }
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
    return data.isList;
  }

  public int varIndex = 0;

  public String getterVarName() {
    return needGetter()
      ? "getter_ref_" + (data.isList ? "list_" : "") + data.typeElement.getSimpleName() + '_' + varIndex()
      : getterCreations.get(0).getterVarName();
  }

  private String gettingMethodName() {
    if (!needGetter()) {
      throw new CommonError(place, "jhb4jhb5 : calling gettingMethodName() for needGetter() == false");
    }
    return "get_ref_" + (data.isList ? "list_" : "") + data.typeElement.getSimpleName() + '_' + varIndex();
  }

  private int varIndex() {
    if (varIndex <= 0) {
      throw new CommonError(place, "j543b667 : Left var index = " + varIndex);
    }
    return varIndex;
  }

  public void writeGetter(int tab, Outer outer) {
    if (!needGetter()) {
      return;
    }
    outer.nl();
    if (data.isList) {
      writeGetterAsList(tab, outer);
    } else {
      writeGetterMono(tab, outer);
    }
  }

  @SuppressWarnings("unused")
  private void writeGetterMono(int tab, Outer outer) {
    throw new CommonError(place, "y65u3i7 : UnsupportedOperationException");
  }

  private void writeGetterAsList(int tab, Outer outer) {
    String typeElementCode = AnnProcUtil.toCode(data.typeElement);

    outer.tab(tab).stn("private final " + Utils.codeName(BeanGetter.class) + "<" + Utils.codeName(List.class)
      + "<" + typeElementCode + ">> " + getterVarName() + " = this::" + gettingMethodName() + ";");
    outer.tab(tab).stn("private " + Utils.codeName(List.class)
      + "<" + typeElementCode + "> " + gettingMethodName() + "() {");

    final int tab1 = tab + 1;

    outer.tab(tab1).stn(Utils.codeName(List.class) + "<" + typeElementCode
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
