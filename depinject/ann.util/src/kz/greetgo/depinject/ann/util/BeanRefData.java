package kz.greetgo.depinject.ann.util;

import javax.lang.model.element.TypeElement;

public class BeanRefData {
  public final TypeElement typeElement;
  public final boolean isList;

  public BeanRefData(TypeElement typeElement, boolean isList) {
    this.typeElement = typeElement;
    this.isList = isList;
  }

  @Override
  public String toString() {
    return "BeanRefData{" + (isList ? "LIST<" : "") + typeElement + '}' + (isList ? ">" : "");
  }
}
