package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.BeanPreparation;
import kz.greetgo.depinject.gen.errors.NoMethodsInBeanContainer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanContainerManager {
  private final Class<?> beanContainerInterface;

  public BeanContainerManager(Class<?> beanContainerInterface) {
    this.beanContainerInterface = beanContainerInterface;
  }

  List<BeanContainerMethod> beanContainerMethodList;
  List<BeanCreation> beanCreationList;
  List<BeanReference> allBeanReferences;
  List<BeanCreation> preparations;

  List<BeanCreation> usingBeanCreationList;
  List<BeanReference> usingBeanReferences;

  List<GetterCreation> writingGetterCreations;

  void prepareToWrite() {
    beanContainerMethodList = BeanContainerMethodExtractor.extract(beanContainerInterface);
    beanCreationList = BeanCreationCollector.collectFrom(beanContainerInterface);

    beanCreationList.forEach(BeanCreation::fillBeanGetterDotList);

    allBeanReferences = new ArrayList<>();
    beanContainerMethodList.forEach(x -> allBeanReferences.add(x.beanReference));

    beanCreationList
      .forEach(a -> a.beanGetterDotList
        .forEach(b -> allBeanReferences.add(b.beanReference)));
    beanCreationList.stream()
      .flatMap(a -> a.getAdditionalBeanReferences().stream())
      .forEachOrdered(a -> allBeanReferences.add(a));

    allBeanReferences.forEach(a -> a.fillTargetCreationsFrom(beanCreationList));

    preparations = beanCreationList.stream()
      .filter(bc -> BeanPreparation.class.isAssignableFrom(bc.beanClass))
      .peek(BeanCreation::calculatePreparingClass)
      .collect(Collectors.toList());

    preparations.forEach(p -> p.calculatesBeanPreparationPriority(preparations));

    preparations.sort(Comparator.comparing(BeanCreation::beanPreparationPriority));

    allBeanReferences.forEach(r -> r.usePreparations(preparations));

    beanContainerMethodList.forEach(a -> a.beanReference.markToUse());

    usingBeanCreationList = beanCreationList.stream().filter(a -> a.use).collect(Collectors.toList());
    usingBeanReferences = allBeanReferences.stream().filter(a -> a.use).collect(Collectors.toList());

    usingBeanReferences.forEach(BeanReference::check);

    int varIndex[] = {1};

    usingBeanCreationList.forEach(a -> a.varIndex = varIndex[0]++);

    usingBeanReferences.stream().filter(BeanReference::needGetter).forEachOrdered(a -> a.varIndex = varIndex[0]++);

    Map<GetterCreation, List<GetterCreation>> getterCreationMap = new HashMap<>();
    usingBeanReferences.stream().flatMap(a -> a.getterCreations.stream())
      .filter(GetterCreation::needGetter)
      .forEachOrdered(a -> getterCreationMap.computeIfAbsent(a, k -> new ArrayList<>()).add(a));

    getterCreationMap.entrySet().forEach(a -> {
      a.getKey().varIndex = varIndex[0]++;
      a.getValue().forEach(b -> b.varIndex = a.getKey().varIndex);
    });
    writingGetterCreations = new ArrayList<>();
    writingGetterCreations.addAll(getterCreationMap.keySet());

    writingGetterCreations.sort(Comparator.comparing(o -> o.beanCreation.beanClass.getName()));

    usingBeanReferences.stream().flatMap(a -> a.getterCreations.stream())
      .filter(GetterCreation::needGetter).forEachOrdered(a -> a.varIndex = varIndex[0]++);

    usingBeanReferences.stream()
      .flatMap(a -> a.getterCreations.stream())
      .filter(a -> a.preparations.size() > 0)
      .forEachOrdered(a -> a.varIndex = varIndex[0]++);
  }

  void writeBeanContainerMethods(int tab, Outer out) {
    if (beanContainerMethodList.isEmpty()) throw new NoMethodsInBeanContainer(beanContainerInterface);

    beanContainerMethodList.forEach(bcm -> bcm.writeBeanContainerMethod(tab, out));
  }

  @SuppressWarnings("SameParameterValue")
  void writeBeanCreations(int tab, Outer out) {
    usingBeanCreationList.forEach(a -> a.writeGetter(tab, out));
  }

  public void writeBeanContainerImpl(Outer outer, String packageName, String classSimpleName) {

    if (packageName != null) {
      outer.stn("package " + packageName + ";");
    }

    outer.stn("public final class " + classSimpleName
      + " implements " + beanContainerInterface.getName().replaceAll("\\$", ".") + '{');

    writeBeanContainerMethods(1, outer);

    outer.tab(1).stn("private final java.lang.Object " + Const.syncField + " = new java.lang.Object();");

    writeBeanCreations(1, outer);

    outer.stn("}");
  }
}
