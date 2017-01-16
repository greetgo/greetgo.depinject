package kz.greetgo.depinject.gen2;

import java.lang.reflect.Method;

import static kz.greetgo.depinject.gen.DepinjectUtil.toCode;

public class BeanContainerMethod implements Comparable<BeanContainerMethod> {

  public final BeanReference beanReference;
  public final Method method;

  public BeanContainerMethod(Method method) {
    this.method = method;
    beanReference = new BeanReference(method.getGenericReturnType(),
      "return type of method " + method.getName() + "() of " + Utils.asStr(method.getDeclaringClass()));
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
}
