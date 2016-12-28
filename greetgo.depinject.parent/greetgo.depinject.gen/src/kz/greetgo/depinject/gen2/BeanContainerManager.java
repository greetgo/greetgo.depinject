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

  void prepareToWrite() {
    beanContainerMethodList = BeanContainerMethodExtractor.extract(beanContainerInterface);
    beanCreationList = BeanCreationCollector.collectFrom(beanContainerInterface);

    beanCreationList.forEach(BeanCreation::fillBeanGetterDotList);

    List<BeanReference> allBeanReferences = new ArrayList<>();
    beanContainerMethodList.forEach(x -> allBeanReferences.add(x.beanReference));

    beanCreationList
      .forEach(a -> a.beanGetterDotList
        .forEach(b -> allBeanReferences.add(b.beanReference)));

    allBeanReferences.forEach(a -> a.fillTargetCreationsFrom(beanCreationList));

    List<BeanCreation> preparations = beanCreationList.stream()
      .filter(bc -> BeanPreparation.class.isAssignableFrom(bc.beanClass))
      .peek(BeanCreation::calculatePreparingClass)
      .collect(Collectors.toList());

    preparations.forEach(p -> p.calculatesBeanPreparationPriority(preparations));

    preparations.sort(Comparator.comparing(BeanCreation::beanPreparationPriority));

    System.out.println("---> BEGIN Preparations:");
    preparations.forEach(System.out::println);
    System.out.println("---> END   Preparations:");

    allBeanReferences.forEach(r -> r.usePreparations(preparations));

    beanContainerMethodList.forEach(a -> a.beanReference.markToUse());

    allBeanReferences.forEach(BeanReference::check);
  }
}
