package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.BeanPreparation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

  void prepareToWrite() {
    beanContainerMethodList = BeanContainerMethodExtractor.extract(beanContainerInterface);
    beanCreationList = BeanCreationCollector.collectFrom(beanContainerInterface);

    beanCreationList.forEach(BeanCreation::fillBeanGetterDotList);

    allBeanReferences = new ArrayList<>();
    beanContainerMethodList.forEach(x -> allBeanReferences.add(x.beanReference));

    beanCreationList
      .forEach(a -> a.beanGetterDotList
        .forEach(b -> allBeanReferences.add(b.beanReference)));

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

    int beanGetterVar[] = {1};

    usingBeanCreationList.forEach(a -> a.beanGetterVar = beanGetterVar[0]++);

    usingBeanReferences.stream()
      .flatMap(a -> a.getterCreations.stream())
      .filter(a -> a.preparations.size() > 0)
      .forEachOrdered(a -> a.beanGetterVar = beanGetterVar[0]++);
  }
}
