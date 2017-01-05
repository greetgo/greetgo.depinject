package kz.greetgo.depinject.gen2;

import java.io.PrintStream;
import java.lang.reflect.Method;

import static kz.greetgo.depinject.gen.DepinjectUtil.toCode;
import static kz.greetgo.depinject.gen2.Tab.tab;

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

  public void writeBeanContainerMethod(int tab, PrintStream out) {

    out.println();
    out.println(tab(tab) + "@java.lang.Override");
    out.println(tab(tab) + toCode(method.getGenericReturnType()) + ' ' + method.getName() + "() {");
    out.println(tab(tab + 1) + "return " + beanReference.getBeanGetterVarName() + ".get();");
    out.println(tab(tab) + '}');

  }
}
