package kz.greetgo.depinject.gen;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class BeanCreationWithFactoryMethod extends BeanCreation {
  public final BeanCreation factorySource;
  public final Method factoryMethod;

  public BeanCreationWithFactoryMethod(Context context, Class<?> beanClass, boolean singleton,
                                       BeanCreation factorySource, Method factoryMethod) {
    super(context, beanClass, singleton);
    if (factorySource == null) {
      throw new NullPointerException("factorySource == null");
    }
    if (factoryMethod == null) {
      throw new NullPointerException("factoryMethod == null");
    }
    this.factorySource = factorySource;
    this.factoryMethod = factoryMethod;
  }


  @Override
  public String toString() {
    //noinspection SpellCheckingInspection
    return (use ? '{' : '(')
        + Utils.asStr(beanClass) + (singleton ? ":SINGLE" : "MULT")
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
    out.tab(tab).stn(Utils.codeName(beanClass) + ' ' + variableName
        + " = " + factorySource.getterVarName() + ".get()." + factoryMethod.getName() + "();");
  }

  @SuppressWarnings("RedundantIfStatement")
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    if (!super.equals(o)) {
      return false;
    }

    BeanCreationWithFactoryMethod that = (BeanCreationWithFactoryMethod) o;

    if (!factorySource.equals(that.factorySource)) {
      return false;
    }

    if (!factoryMethod.equals(that.factoryMethod)) {
      return false;
    }

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
