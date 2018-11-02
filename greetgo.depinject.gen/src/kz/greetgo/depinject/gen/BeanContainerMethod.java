package kz.greetgo.depinject.gen;

import java.lang.reflect.Method;

import static kz.greetgo.depinject.gen.BeanReferencePlace.placeInBeanContainerMethod;
import static kz.greetgo.depinject.gen.DepinjectUtil.toCode;

public class BeanContainerMethod implements Comparable<BeanContainerMethod> {

  public final BeanReference beanReference;
  public final Method method;

  public BeanContainerMethod(Context context, Method method) {
    this.method = method;

    BeanReference.Place place = placeInBeanContainerMethod(method);

    beanReference = context.newBeanReference(method.getGenericReturnType(), place);
  }

  @Override
  @SuppressWarnings("NullableProblems")
  public int compareTo(BeanContainerMethod o) {
    return compareStr().compareTo(o.compareStr());
  }

  private String compareStr() {
    return method.getName();
  }

  public void writeBeanContainerMethod(int tab, Outer out) {

    out.nl();
    out.tab(tab).stn("@java.lang.Override");
    out.tab(tab).stn("public " + toCode(method.getGenericReturnType()) + ' ' + method.getName() + "() {");
    out.tab(tab + 1).stn("return " + beanReference.getterVarName() + ".get();");
    out.tab(tab).stn("}");

  }

  public void markToUse() {
    beanReference.markToUse();
  }

  @Override
  public String toString() {
    return "Method " + method.getName() + "() ::: " + beanReference.toFullString();
  }
}
