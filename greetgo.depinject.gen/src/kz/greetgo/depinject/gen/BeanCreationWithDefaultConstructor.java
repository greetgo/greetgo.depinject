package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.LetBeNonePublic;
import kz.greetgo.depinject.gen.errors.BeanGetterIsNotPublic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

public class BeanCreationWithDefaultConstructor extends BeanCreation {
  public BeanCreationWithDefaultConstructor(Context context, Class<?> beanClass, boolean singleton) {
    super(context, beanClass, singleton);
  }

  @Override
  public List<BeanReference> getAdditionalBeanReferences() {
    return Collections.emptyList();
  }

  @Override
  public String toString() {
    //noinspection SpellCheckingInspection
    return (use ? '{' : '(')
      + Utils.asStr(beanClass) + (singleton ? ":SINGLE" : "MULT") + " created by def constructor"
      + preparationInfo()
      + (use ? '}' : ')');
  }

  @Override
  protected void markToUseAdditions() {
    //Nothing to do
  }

  @Override
  protected void writeCreateBean(int tab, Outer out, String variableName) {
    out.tab(tab).stn(Utils.codeName(beanClass) + ' ' + variableName + " = new " + Utils.codeName(beanClass) + "();");
  }

  @Override
  public void checkBeanGetterNotPublic() {
    checkBeanGetterNotPublicFor(beanClass, null);

    Class<?> me = beanClass;

    while (true) {
      Class<?> parent = me.getSuperclass();
      if (Object.class.equals(parent)) { return; }
      checkBeanGetterNotPublicFor(parent, beanClass);
      me = parent;
    }
  }

  private static void checkBeanGetterNotPublicFor(Class<?> aClass, Class<?> beanClass) {
    if (aClass.getAnnotation(LetBeNonePublic.class) != null) { return; }

    for (Field field : aClass.getDeclaredFields()) {
      if (Modifier.isPublic(field.getModifiers())) { continue; }

      if (!BeanGetter.class.equals(field.getType())) { continue; }
      if (field.getAnnotation(LetBeNonePublic.class) != null) { continue; }
      throw new BeanGetterIsNotPublic(aClass, field, beanClass);
    }
  }
}
