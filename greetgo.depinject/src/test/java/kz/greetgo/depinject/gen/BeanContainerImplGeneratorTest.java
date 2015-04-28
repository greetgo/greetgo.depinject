package kz.greetgo.depinject.gen;

import static org.fest.assertions.api.Assertions.assertThat;
import kz.greetgo.depinject.gen.example.TestBeanContainer;
import kz.greetgo.depinject.gen.example.TestBeanContainerNoInclude;
import kz.greetgo.depinject.gen.example.beans1.AsdBeanImpl;
import kz.greetgo.depinject.gen.example.beans2.place1.asd.Place1bean;
import kz.greetgo.depinject.gen.example.beans2.place2.asd.Place2bean;
import kz.greetgo.depinject.src.Bean;

import org.testng.annotations.Test;

public class BeanContainerImplGeneratorTest {
  
  @Test(expectedExceptions = NoInterface.class)
  public void findBeanClassSet_notIface() throws Exception {
    BeanContainerImplGenerator x = new BeanContainerImplGenerator();
    x.beanContainerIface = Integer.class;
    
    x.findBeanClassSet();
  }
  
  @Test(expectedExceptions = NoBeanContainer.class)
  public void findBeanClassSet_noBeanContainer() throws Exception {
    BeanContainerImplGenerator x = new BeanContainerImplGenerator();
    x.beanContainerIface = Bean.class;
    
    x.findBeanClassSet();
  }
  
  @Test(expectedExceptions = NoInclude.class)
  public void findBeanClassSet_noInclude() throws Exception {
    BeanContainerImplGenerator x = new BeanContainerImplGenerator();
    x.beanContainerIface = TestBeanContainerNoInclude.class;
    
    x.findBeanClassSet();
  }
  
  @Test
  public void findBeanClassSet_ok() throws Exception {
    BeanContainerImplGenerator x = new BeanContainerImplGenerator();
    x.beanContainerIface = TestBeanContainer.class;
    
    x.findBeanClassSet();
    
    assertThat(x.beanClassSet).contains(AsdBeanImpl.class, Place1bean.class, Place2bean.class);
    
    System.out.println(x.beanClassSet);
  }
  
}
