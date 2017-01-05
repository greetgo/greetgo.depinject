package kz.greetgo.depinject.gen2;

import java.util.ArrayList;
import java.util.List;

public class GetterCreation {
  public final Class<?> getterClass;
  public final BeanCreation beanCreation;

  public GetterCreation(Class<?> getterClass, BeanCreation beanCreation) {
    if (getterClass == null) throw new NullPointerException("getterClass == null");
    if (beanCreation == null) throw new NullPointerException("beanCreation == null");
    this.getterClass = getterClass;
    this.beanCreation = beanCreation;
  }

  public boolean use = false;
  
  public void markToUse() {
    if (use) return;
    use = true;
    beanCreation.markToUse();
    preparations.forEach(BeanCreation::markToUse);
  }

  public final List<BeanCreation> preparations = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GetterCreation that = (GetterCreation) o;

    if (!beanCreation.equals(that.beanCreation)) return false;
    if (!preparations.equals(that.preparations)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = beanCreation.hashCode();
    result = 31 * result + preparations.hashCode();
    return result;
  }

  public int varIndex = 0;

  public boolean needGetter() {
    return preparations.size() > 0;
  }

  public String getBeanGetterVarName() {
    return needGetter()
      ? "getter_withPreparations_" + beanCreation.beanClass.getSimpleName() + '_' + varIndex()
      : beanCreation.getBeanGetterVarName();
  }

  private int varIndex() {
    if (varIndex <= 0) throw new RuntimeException("Left var index = " + varIndex);
    return varIndex;
  }

  public void usePreparations(List<BeanCreation> allPreparations) {
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
    return sb.toString();
  }
}
