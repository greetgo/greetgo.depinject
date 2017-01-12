package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.BeanPreparation;
import kz.greetgo.depinject.core.BeanPreparationPriority;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.depinject.gen.errors.IllegalBeanGetterDefinition;
import kz.greetgo.depinject.gen.errors.LeftException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static kz.greetgo.depinject.gen2.Utils.codeName;
import static kz.greetgo.depinject.gen2.Utils.extractRawClass;

public abstract class BeanCreation {
  public final Class<?> beanClass;
  public final boolean singleton;

  public int varIndex;

  public BeanCreation(Class<?> beanClass, boolean singleton) {
    this.beanClass = beanClass;
    this.singleton = singleton;
  }

  public String getterVarName() {
    if (varIndex <= 0) throw new LeftException("Left varIndex value = " + varIndex);
    return "getter_native_" + beanClass.getSimpleName() + '_' + varIndex;
  }

  private String gettingMethodName() {
    if (varIndex <= 0) throw new LeftException("Left varIndex value = " + varIndex);
    return "get_native_" + beanClass.getSimpleName() + '_' + varIndex;
  }

  public String cachedValueVarName() {
    if (varIndex <= 0) throw new LeftException("Left varIndex value = " + varIndex);
    return "cachedValue_native_" + beanClass.getSimpleName() + '_' + varIndex;
  }

  public abstract List<BeanReference> getAdditionalBeanReferences();

  public final List<BeanGetterDot> beanGetterDotList = new ArrayList<>();

  public void fillBeanGetterDotList() {
    fillBeanGetterDotListInner(beanGetterDotList, beanClass);
  }

  static void fillBeanGetterDotListInner(List<BeanGetterDot> beanGetterDotList, Class<?> beanClass) {

    for (Field field : beanClass.getFields()) {
      if (field.getType() == BeanGetter.class) {
        addDot(beanGetterDotList, field.getName(), field.getGenericType(), beanClass);
      }
    }

    Collections.sort(beanGetterDotList);
  }

  private static void addDot(List<BeanGetterDot> list, String fieldName, Type beanGetterType, Class<?> beanClass) {
    if (!(beanGetterType instanceof ParameterizedType)) {
      throw new IllegalBeanGetterDefinition(beanClass, fieldName);
    }
    ParameterizedType pt = (ParameterizedType) beanGetterType;
    BeanReference beanReference = new BeanReference(pt.getActualTypeArguments()[0],
      "field " + fieldName + " of " + Utils.asStr(beanClass));
    list.add(new BeanGetterDot(fieldName, beanReference));
  }

  public boolean use = false;

  public void markToUse() {
    if (use) return;
    use = true;
    beanGetterDotList.forEach(a -> a.beanReference.markToUse());
    markToUseAdditions();
  }

  protected abstract void markToUseAdditions();


  public void writeGetter(int tab, Outer out) {
    out.nl();

    final int tab1 = tab + 1;
    final int tab2 = tab + 2;
    final int tab3 = tab + 3;

    if (singleton) {
      out.tab(tab).stn("private final " + codeName(AtomicReference.class) + "<" + codeName(beanClass) +
        "> " + cachedValueVarName() + " = new " + codeName(AtomicReference.class) + "<>(null);");
    }

    out.tab(tab).stn("private final " + codeName(BeanGetter.class)
      + "<" + codeName(beanClass) + "> " + getterVarName() + " = this::" + gettingMethodName() + ";");
    out.tab(tab).stn("private " + codeName(beanClass) + " " + gettingMethodName() + " () {");

    if (singleton) {

      out.tab(tab1).stn("{");
      out.tab(tab2).stn(codeName(beanClass) + " x = " + cachedValueVarName() + ".get();");
      out.tab(tab2).stn("if (x != null) return x;");
      out.tab(tab1).stn("}");

      out.tab(tab1).stn("synchronized (" + Const.SYNC_FIELD + ") {");

      out.tab(tab2).stn("{");
      out.tab(tab3).stn(codeName(beanClass) + " x = " + cachedValueVarName() + ".get();");
      out.tab(tab3).stn("if (x != null) return x;");
      out.tab(tab2).stn("}");

      {
        out.tab(tab2).stn("try {");

        writeCreateBean(tab + 3, out, "localValue");
        writeBeanGettersAndInit(tab + 3, out, "localValue");

        out.tab(tab3).stn(cachedValueVarName() + ".set(localValue);");
        out.tab(tab3).stn("return localValue;");

        out.tab(tab2).stn("} catch (java.lang.Exception e) {");
        out.tab(tab3).stn("if (e instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException) e;");
        out.tab(tab3).stn("throw new java.lang.RuntimeException(e);");
        out.tab(tab2).stn("}");
      }

      out.tab(tab1).stn("}");//synchronized

    } else {

      out.tab(tab1).stn("try {");
      writeCreateBean(tab2, out, "localValue");
      writeBeanGettersAndInit(tab2, out, "localValue");
      out.tab(tab2).stn("return localValue;");
      out.tab(tab1).stn("} catch (java.lang.Exception e) {");
      out.tab(tab2).stn("if (e instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException) e;");
      out.tab(tab2).stn("throw new java.lang.RuntimeException(e);");
      out.tab(tab1).stn("}");
      RuntimeException e;
    }

    out.tab(tab).stn("}");
  }

  public void writeBeanGettersAndInit(int tab, Outer out, @SuppressWarnings("SameParameterValue") String variableName) {
    beanGetterDotList.forEach(bg -> bg.writeAssignment(tab, out, variableName));
    if (HasAfterInject.class.isAssignableFrom(beanClass)) {
      out.tab(tab).stn(variableName + ".afterInject();");
    }
  }

  @SuppressWarnings("SameParameterValue")
  protected abstract void writeCreateBean(int tab, Outer out, String variableName);

  public static class BeanPreparationPriorityDot implements Comparable<BeanPreparationPriorityDot> {
    int parenting = 0;
    double fromAnnotation = 0;

    @Override
    @SuppressWarnings("NullableProblems")
    public int compareTo(BeanPreparationPriorityDot o) {
      {
        int cmp = Integer.compare(parenting, o.parenting);
        if (cmp != 0) return cmp;
      }
      return Double.compare(fromAnnotation, o.fromAnnotation);
    }

    @Override
    public String toString() {
      return "(" + parenting + ", " + fromAnnotation + ')';
    }
  }

  private final BeanPreparationPriorityDot beanPreparationPriority = new BeanPreparationPriorityDot();

  public void calculatesBeanPreparationPriority(List<BeanCreation> preparations) {
    {
      BeanPreparationPriority annotation = Utils.getAnnotation(beanClass, BeanPreparationPriority.class);
      if (annotation != null) beanPreparationPriority.fromAnnotation = annotation.value();
    }

    preparations.stream()
      .filter(bc -> bc.preparingClass != null)
      .forEach(that -> {
        if (that.preparingClass.isAssignableFrom(this.preparingClass)) {
          that.beanPreparationPriority.parenting++;
        }
      });
  }

  public BeanPreparationPriorityDot beanPreparationPriority() {
    return beanPreparationPriority;
  }

  public Class<?> preparingClass = null;

  public void calculatePreparingClass() {
    preparingClass = getPreparingClass(beanClass, new HashSet<>());
  }

  static Class<?> getPreparingClass(Type type, Set<Class<?>> cacheSet) {

    if (type == null) return null;

    if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      Type rawType = parameterizedType.getRawType();

      if (rawType instanceof Class) {
        Class<?> rawClass = (Class<?>) rawType;
        if (BeanPreparation.class == rawClass) {
          Type typeArg0 = parameterizedType.getActualTypeArguments()[0];
          return extractRawClass(typeArg0);
        }
      }

      type = extractRawClass(rawType);
    }

    if (type instanceof Class) {
      Class<?> aClass = (Class<?>) type;
      if (cacheSet.contains(aClass)) return null;
      cacheSet.add(aClass);

      for (Type interfaceType : aClass.getGenericInterfaces()) {
        Class<?> ret = getPreparingClass(interfaceType, cacheSet);
        if (ret != null) return ret;
      }

      return getPreparingClass(aClass.getSuperclass(), cacheSet);
    }

    throw new IllegalArgumentException("Unknown type: " + type);
  }

  protected String preparationInfo() {
    if (preparingClass == null) return "";
    return ", preparation for " + Utils.asStr(preparingClass) + ' ' + beanPreparationPriority.toString();
  }
}
