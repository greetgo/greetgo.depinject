package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanFactory;

import java.util.Collections;
import java.util.List;

public class BeanCreationWithBeanFactory extends BeanCreation {
  public final BeanReference beanFactorySource;

  public BeanCreationWithBeanFactory(Context context,
                                     Class<?> beanClass,
                                     boolean singleton,
                                     BeanReference beanFactorySource) {
    super(context, beanClass, singleton);
    if (!BeanFactory.class.isAssignableFrom(beanFactorySource.sourceClass)) {
      throw new RuntimeException(beanFactorySource.sourceClass + " is not bean factory; " + beanFactorySource.place);
    }
    this.beanFactorySource = beanFactorySource;
  }

  @Override
  public String toString() {
    return (use ? '{' : '(')
      + Utils.asStr(beanClass) + (singleton ? ":SINGLE" : "MULT")
      + " created by " + beanFactorySource.firstBeanToString()
      + preparationInfo()
      + (use ? '}' : ')');
  }

  @Override
  public List<BeanReference> getAdditionalBeanReferences() {
    return Collections.singletonList(beanFactorySource);
  }

  @Override
  protected void markToUseAdditions() {
    beanFactorySource.markToUse();
  }

  @Override
  protected void writeCreateBean(int tab, Outer out, String variableName) {
    out.tab(tab).stn(Utils.codeName(beanClass) + ' ' + variableName
      + " = (" + Utils.codeName(beanClass) + ") " + beanFactorySource.getterVarName()
      + ".get().createBean(" + Utils.codeName(beanClass) + ".class);");
  }
}
