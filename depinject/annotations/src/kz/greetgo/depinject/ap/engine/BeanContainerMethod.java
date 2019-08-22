package kz.greetgo.depinject.ap.engine;

import kz.greetgo.depinject.ann.util.AnnProcUtil;

import javax.lang.model.element.ExecutableElement;

import static kz.greetgo.depinject.ap.engine.BeanReferencePlace.placeInBeanContainerMethod;

public class BeanContainerMethod implements Comparable<BeanContainerMethod> {

  public final BeanReference beanReference;
  public final ExecutableElement method;

  public BeanContainerMethod(Context context, ExecutableElement method) {
    this.method = method;

    BeanReference.Place place = placeInBeanContainerMethod(method);

    beanReference = context.newBeanReference(method.getReturnType(), place);
  }

  @Override
  public int compareTo(BeanContainerMethod o) {
    return compareStr().compareTo(o.compareStr());
  }

  private String compareStr() {
    return method.getSimpleName().toString();
  }

  public void writeBeanContainerMethod(int tab, Outer out) {

    out.nl();
    out.tab(tab).stn("@java.lang.Override");
    out.tab(tab).stn("public " + AnnProcUtil.toCode(method.getReturnType()) + ' ' + method.getSimpleName() + "() {");
    out.tab(tab + 1).stn("return " + beanReference.getterVarName() + ".get();");
    out.tab(tab).stn("}");

  }

  public void markToUse() {
    beanReference.markToUse();
  }

  @Override
  public String toString() {
    return "Method " + method.getSimpleName() + "() ::: " + beanReference.toFullString();
  }
}
