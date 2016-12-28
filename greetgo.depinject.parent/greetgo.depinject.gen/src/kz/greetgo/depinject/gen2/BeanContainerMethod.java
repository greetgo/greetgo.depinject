package kz.greetgo.depinject.gen2;

import java.lang.reflect.Method;

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
}
