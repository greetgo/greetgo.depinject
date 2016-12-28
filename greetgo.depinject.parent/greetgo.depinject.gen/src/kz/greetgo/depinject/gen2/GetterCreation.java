package kz.greetgo.depinject.gen2;

import java.util.ArrayList;
import java.util.List;

public class GetterCreation extends AbstractGetterCreation {
  public final Class<?> getterClass;
  public final BeanCreation beanCreation;

  public GetterCreation(Class<?> getterClass, BeanCreation beanCreation) {
    this.getterClass = getterClass;
    this.beanCreation = beanCreation;
  }

  @Override
  public void markToUse() {
    if (use) return;
    use = true;
    beanCreation.markToUse();
    preparations.forEach(BeanCreation::markToUse);
  }

  public final List<BeanCreation> preparations = new ArrayList<>();

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
