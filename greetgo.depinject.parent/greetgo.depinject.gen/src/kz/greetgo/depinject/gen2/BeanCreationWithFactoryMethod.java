package kz.greetgo.depinject.gen2;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import static kz.greetgo.depinject.gen2.Utils.asStr;
import static kz.greetgo.depinject.gen2.Utils.codeName;

public class BeanCreationWithFactoryMethod extends BeanCreation {
  public final BeanCreation factorySource;
  public final Method factoryMethod;

  public BeanCreationWithFactoryMethod(Class<?> beanClass, boolean singleton,
                                       BeanCreation factorySource, Method factoryMethod) {
    super(beanClass, singleton);
    if (factorySource == null) throw new NullPointerException("factorySource == null");
    if (factoryMethod == null) throw new NullPointerException("factoryMethod == null");
    this.factorySource = factorySource;
    this.factoryMethod = factoryMethod;
  }


  @Override
  public String toString() {
    return (use ? '{' : '(')
      + asStr(beanClass) + (singleton ? ":SINGLE" : "MULT")
      + " created by method " + factoryMethod.getName() + "() of " + factorySource
      + preparationInfo()
      + (use ? '}' : ')');
  }

  @Override
  public List<BeanReference> getAdditionalBeanReferences() {
    return Collections.emptyList();
  }

  @Override
  protected void markToUseAdditions() {
    factorySource.markToUse();
  }

  @Override
  protected void writeCreateBean(int tab, Outer out, String variableName) {
    out.tab(tab).stn(codeName(beanClass) + ' ' + variableName
      + " = " + factorySource.getterVarName() + ".get()." + factoryMethod.getName() + "();");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    BeanCreationWithFactoryMethod that = (BeanCreationWithFactoryMethod) o;

    if (!factorySource.equals(that.factorySource)) return false;
    if (!factoryMethod.equals(that.factoryMethod)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + factorySource.hashCode();
    result = 31 * result + factoryMethod.hashCode();
    return result;
  }
}
