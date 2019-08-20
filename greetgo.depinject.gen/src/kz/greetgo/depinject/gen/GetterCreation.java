package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.replace.BeanReplacer;
import kz.greetgo.depinject.gen.errors.LeftException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GetterCreation {
  public final Class<?> getterClass;
  public final BeanCreation beanCreation;

  public GetterCreation(Class<?> getterClass, BeanCreation beanCreation) {
    if (getterClass == null) {
      throw new NullPointerException("getterClass == null");
    }

    if (beanCreation == null) {
      throw new NullPointerException("beanCreation == null");
    }

    this.getterClass = getterClass;
    this.beanCreation = beanCreation;
  }

  @Override
  public String toString() {
    return "GetterCreation{(" + varIndex + ") " + Utils.asStr(getterClass) + " := " + beanCreation;
  }

  public boolean use = false;

  public void markToUse() {
    if (use) {
      return;
    }
    use = true;
    beanCreation.markToUse();
    preparations.forEach(BeanCreation::markToUse);
    replacers.forEach(BeanCreation::markToUse);
  }

  public final List<BeanCreation> preparations = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    GetterCreation that = (GetterCreation) o;

    if (!beanCreation.equals(that.beanCreation)) {
      return false;
    }
    if (!preparations.equals(that.preparations)) {
      return false;
    }
    if (!replacers.equals(that.replacers)) {
      return false;
    }

    //noinspection RedundantIfStatement
    if (replacers.size() > 0 && !getterClass.equals(that.getterClass)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = beanCreation.hashCode();
    result = 31 * result + preparations.hashCode();
    result = 31 * result + replacers.hashCode();
    if (replacers.size() > 0) {
      result = 31 * result + getterClass.hashCode();
    }
    return result;
  }

  public int varIndex = 0;

  public boolean needGetter() {
    return preparations.size() > 0 || replacers.size() > 0;
  }

  private String className() {
    return beanCreation.beanClass.getSimpleName();
  }

  private int varIndex() {
    if (varIndex <= 0) {
      throw new RuntimeException("Left var index = " + varIndex);
    }
    return varIndex;
  }

  private boolean wasUsePreparations = false;

  public void usePreparations(List<BeanCreation> allPreparations) {
    if (wasUsePreparations) {
      return;
    }
    wasUsePreparations = true;

    Class<?> currentClass = beanCreation.beanClass;
    for (BeanCreation preparation : allPreparations) {
      Class<?> pc = preparation.preparingClass;
      if (pc != null && pc.isAssignableFrom(currentClass) && getterClass.isAssignableFrom(pc)) {
        preparations.add(preparation);
        currentClass = pc;
      }
    }
  }

  public String preparationStr() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n\t").append(Utils.asStr(getterClass)).append(" -> ").append(beanCreation);
    for (BeanCreation preparation : preparations) {
      sb.append("\n\t\t\t\tprepared by ").append(preparation);
    }
    for (BeanCreation replacer : replacers) {
      sb.append("\n\t\t\t\treplaced by ").append(replacer);
    }
    return sb.toString();
  }

  public void writeGetter(int tab, Outer outer) {
    if (!needGetter()) { return; }
    if (beanCreation.isSingleton()) {
      writeGetterSingleton(tab, outer);
    } else {
      writeGetterMulti(tab, outer);
    }
  }

  private String applyPreparations(int tab, Outer outer, String inVarName) {
    String current = inVarName;

    int i = 1;

    for (BeanCreation p : preparations) {

      String newVar = inVarName + '_' + i++;

      outer.tab(tab).stn(Utils.codeName(p.preparingClass) + " " + newVar
          + " = " + p.getterVarName() + ".get().prepareBean(" + current + ");");

      current = newVar;
    }

    return current;
  }

  private String applyReplacers(int tab, Outer o, String varName) {
    String var = null;

    for (BeanCreation replacer : replacers) {
      if (var == null) {
        var = "replacer_" + varName;
        o.tab(tab).stn(Utils.codeName(getterClass) + ' ' + var + " = " + replaceCode(replacer, varName) + ';');
      } else {
        o.tab(tab).stn(var + " = " + replaceCode(replacer, var) + ';');
      }
    }

    return var == null ? varName : var;
  }

  private String replaceCode(BeanCreation replacer, String varName) {
    return "(" + Utils.codeName(getterClass) + ") " + replacer.getterVarName()
        + ".get().replaceBean(" + varName + ", " + Utils.codeName(getterClass) + ".class)";
  }


  public String getterVarName() {
    return needGetter()
        ? "getter_withPreparations_" + className() + '_' + varIndex()
        : beanCreation.getterVarName();
  }

  private String cachedValueVarName() {
    if (!needGetter()) {
      throw new LeftException("gje4kkf556djd5h3");
    }
    return "cachedValue_withPreparations_" + className() + '_' + varIndex();
  }

  private String gettingMethodName() {
    if (!needGetter()) {
      throw new LeftException("hs74fh64h74ht56feh5");
    }
    return "get_withPreparations_" + className() + '_' + varIndex();
  }

  private void writeGetterSingleton(int tab, Outer o) {
    String getterClassName = Utils.codeName(getterClass);
    String beanClassName = Utils.codeName(beanCreation.beanClass);

    o.nl();
    o.tab(tab).stn("private final " + Utils.codeName(AtomicReference.class) + "<" + getterClassName
        + "> " + cachedValueVarName() + " = new " + Utils.codeName(AtomicReference.class) + "<>();");
    o.tab(tab).stn("private final " + Utils.codeName(BeanGetter.class) + "<" + getterClassName
        + "> " + getterVarName() + " = this::" + gettingMethodName() + ";");

    o.tab(tab).stn("private " + getterClassName + ' ' + gettingMethodName() + " () {");

    final int tab1 = tab + 1;
    final int tab2 = tab + 2;
    final int tab3 = tab + 3;

    o.tab(tab1).stn("{");
    o.tab(tab2).stn(getterClassName + " x = " + cachedValueVarName() + ".get();");
    o.tab(tab2).stn("if (x != null) return x;");
    o.tab(tab1).stn("}");

    o.tab(tab1).stn("synchronized (" + Const.SYNC_FIELD + ") {");

    o.tab(tab2).stn("{");
    o.tab(tab3).stn(getterClassName + " x = " + cachedValueVarName() + ".get();");
    o.tab(tab3).stn("if (x != null) return x;");
    o.tab(tab2).stn("}");

    o.tab(tab2).stn("{");
    o.tab(tab3).stn(beanClassName + " singleValue = " + beanCreation.getterVarName() + ".get();");
    String outPreparationsVarName = applyPreparations(tab3, o, "singleValue");
    String outReplacersVarName = applyReplacers(tab3, o, outPreparationsVarName);
    o.tab(tab3).stn(cachedValueVarName() + ".set(" + outReplacersVarName + ");");
    o.tab(tab3).stn("return " + outReplacersVarName + ';');
    o.tab(tab2).stn("}");

    o.tab(tab1).stn("}");//synchronized

    o.tab(tab).stn("}");
  }

  private void writeGetterMulti(int tab, Outer o) {
    String getterClassName = Utils.codeName(getterClass);
    String beanClassName = Utils.codeName(beanCreation.beanClass);

    o.nl();
    o.tab(tab).stn("private final " + Utils.codeName(BeanGetter.class) + "<" + getterClassName
        + "> " + getterVarName() + " = this::" + gettingMethodName() + ";");

    o.tab(tab).stn("private " + getterClassName + ' ' + gettingMethodName() + " () {");

    final int tab1 = tab + 1;

    o.tab(tab1).stn(beanClassName + " value = " + beanCreation.getterVarName() + ".get();");
    String outPreparationsVarName = applyPreparations(tab1, o, "value");
    String outReplacersVarName = applyReplacers(tab1, o, outPreparationsVarName);
    o.tab(tab1).stn("return " + outReplacersVarName + ';');

    o.tab(tab).stn("}");
  }

  public final List<BeanCreation> replacers = new ArrayList<>();

  private boolean wasUseReplacers = false;

  public void useReplacers(List<BeanCreation> allReplacers) {
    if (wasUseReplacers) {
      return;
    }
    wasUseReplacers = true;

    Class<?> checkingClass = beanCreation.beanClass;
    if (BeanReplacer.class.isAssignableFrom(checkingClass)) {
      return;
    }

    for (BeanCreation replacer : allReplacers) {
      if (replacer.replaceChecker != null && replacer.replaceChecker.check(checkingClass)) {
        replacers.add(replacer);
      }
    }
  }

}
