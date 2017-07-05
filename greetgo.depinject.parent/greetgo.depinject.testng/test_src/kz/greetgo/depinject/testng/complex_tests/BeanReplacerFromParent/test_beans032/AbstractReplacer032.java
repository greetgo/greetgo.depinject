package kz.greetgo.depinject.testng.complex_tests.BeanReplacerFromParent.test_beans032;

import kz.greetgo.depinject.core.replace.BeanReplacer;

import java.lang.reflect.Proxy;

public abstract class AbstractReplacer032 implements BeanReplacer {
  @Override
  public Object replaceBean(Object originalBean, Class<?> returnClass) {
    replaceBeanClass(returnClass.getSimpleName());
    if (!returnClass.isInterface()) return originalBean;

    return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{returnClass}, (proxy, method, args) -> {

      Log032.logs032.add("AbstractReplacer032 called method " + method.getName());

      if (method.getName().equals("toString") && method.getParameterTypes().length == 0) {
        return "[PROXY FOR " + originalBean.toString() + "]";
      }

      return method.invoke(originalBean, args);
    });
  }

  protected abstract void replaceBeanClass(String className);
}
