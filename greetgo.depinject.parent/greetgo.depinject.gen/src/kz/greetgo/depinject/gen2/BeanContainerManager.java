package kz.greetgo.depinject.gen2;

import java.util.List;

public class BeanContainerManager {
  private final Class<?> beanContainerInterface;

  public BeanContainerManager(Class<?> beanContainerInterface) {
    this.beanContainerInterface = beanContainerInterface;
  }

  List<BeanContainerMethod> beanContainerMethodList;
  List<BeanCreation> beanCreationList;
  
  public void prepareToWrite() {
    beanContainerMethodList = BeanContainerMethodExtractor.extract(beanContainerInterface);
    beanCreationList = BeanCreationCollector.collectFrom(beanContainerInterface);
    
    
    
  }
  
}
