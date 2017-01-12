package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.errors.FactoryMethodCannotContainAnyArguments;
import kz.greetgo.depinject.gen.errors.NoBeanConfig;
import kz.greetgo.depinject.gen.errors.NoBeanContainer;
import kz.greetgo.depinject.gen.errors.NoDefaultBeanFactory;
import kz.greetgo.depinject.gen.errors.NoInclude;
import kz.greetgo.depinject.gen2.test_beans001.BeanConfig001;
import kz.greetgo.depinject.gen2.test_beans002.BeanConfig002;
import kz.greetgo.depinject.gen2.test_beans003.Bean1;
import kz.greetgo.depinject.gen2.test_beans003.Bean2;
import kz.greetgo.depinject.gen2.test_beans003.BeanConfig003;
import kz.greetgo.depinject.gen2.test_beans003.BeanFactory;
import kz.greetgo.depinject.gen2.test_beans004.BeanConfig004;
import kz.greetgo.depinject.gen2.test_beans005.sub_beans_1.BeanFactory1;
import kz.greetgo.depinject.gen2.test_beans005.sub_beans_2.BeanFactory2;
import kz.greetgo.depinject.gen2.test_beans005.sub_beans_4.BeanFactory4;
import kz.greetgo.depinject.gen2.test_beans005.sub_beans_5.BeanFactory5;
import kz.greetgo.depinject.gen2.test_beans005.top.BeanConfig005;
import kz.greetgo.depinject.gen2.test_beans006.BeanConfig006;
import kz.greetgo.depinject.gen2.test_beans012.BeanConfig012;
import kz.greetgo.depinject.gen2.test_beans013.BeanConfig013;
import kz.greetgo.depinject.gen2.test_beans014.BeanConfig014_2;
import kz.greetgo.depinject.gen2.test_beans014.BeanConfig014_3;
import kz.greetgo.depinject.gen2.test_beans014.local_package.sub.BeanConfig014_1;
import kz.greetgo.depinject.gen2.test_beans014.remote_package.sub.RemoteBean;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class BeanCreationCollectorTest {

  public interface BeanContainerWithoutNoBeanContainer {
  }

  @Test(expectedExceptions = NoBeanContainer.class)
  public void collectFrom_NoBeanContainer() throws Exception {
    //
    //
    BeanCreationCollector.collectFrom(BeanContainerWithoutNoBeanContainer.class);
    //
    //
  }

  public interface BeanContainerWithoutInclude extends BeanContainer {
  }

  @Test(expectedExceptions = NoInclude.class)
  public void collectFrom_NoInclude() throws Exception {
    //
    //
    BeanCreationCollector.collectFrom(BeanContainerWithoutInclude.class);
    //
    //
  }

  class BeanConfigWithoutBeanConfig {
  }

  @Include(BeanConfigWithoutBeanConfig.class)
  public interface ForNoBeanConfigError extends BeanContainer {
  }

  @Test(expectedExceptions = NoBeanConfig.class)
  public void collectFrom_NoBeanConfig() throws Exception {
    //
    //
    BeanCreationCollector.collectFrom(ForNoBeanConfigError.class);
    //
    //
  }

  @Include(BeanConfig001.class)
  public interface HasBeanWithDefaultConstructor extends BeanContainer {
  }

  private static Map<String, BeanCreation> toMapSimple(List<BeanCreation> list) {
    Map<String, BeanCreation> ret = new HashMap<>();
    list.forEach(bc -> ret.put(bc.beanClass.getSimpleName(), bc));
    return ret;
  }

  @Test
  public void collectFrom_BeanWithDefaultConstructor() throws Exception {
    //
    //
    List<BeanCreation> list = BeanCreationCollector.collectFrom(HasBeanWithDefaultConstructor.class);
    //
    //

    assertThat(list).hasSize(2);

    Map<String, BeanCreation> map = toMapSimple(list);

    assertThat(map.get("BeanWithDefaultConstructor1")).isInstanceOf(BeanCreationWithDefaultConstructor.class);
    assertThat(map.get("BeanWithDefaultConstructor2")).isInstanceOf(BeanCreationWithDefaultConstructor.class);

    assertThat(map.get("BeanWithDefaultConstructor1").singleton).isTrue();
    assertThat(map.get("BeanWithDefaultConstructor2").singleton).isFalse();
  }

  @Include(BeanConfig002.class)
  interface WithoutBeanScanner extends BeanContainer {
  }

  @Test
  public void collectFrom_withoutBeanScanner() throws Exception {
    //
    //
    List<BeanCreation> list = BeanCreationCollector.collectFrom(WithoutBeanScanner.class);
    //
    //

    assertThat(list).isEmpty();
  }

  @Include(BeanConfig003.class)
  interface FactoryMethodBeanContainer extends BeanContainer {
  }

  @Test
  public void collectFrom_factoryMethod() {
    //
    //
    List<BeanCreation> list = BeanCreationCollector.collectFrom(FactoryMethodBeanContainer.class);
    //
    //

    assertThat(list).hasSize(3);

    Map<String, BeanCreation> map = toMapSimple(list);

    assertThat(map.get(BeanFactory.class.getSimpleName())).isInstanceOf(BeanCreationWithDefaultConstructor.class);
    assertThat(map.get(Bean1.class.getSimpleName())).isInstanceOf(BeanCreationWithFactoryMethod.class);
    assertThat(map.get(Bean2.class.getSimpleName())).isInstanceOf(BeanCreationWithFactoryMethod.class);

    BeanCreationWithFactoryMethod bc = (BeanCreationWithFactoryMethod) map.get(Bean1.class.getSimpleName());

    assertThat(bc.factorySource.beanClass.getName()).isEqualTo(BeanFactory.class.getName());
    assertThat(bc.factoryMethod.getName()).isEqualTo("createBean1");

    assertThat(map.get(BeanFactory.class.getSimpleName()).singleton).isTrue();
    assertThat(map.get(Bean1.class.getSimpleName()).singleton).isTrue();
    assertThat(map.get(Bean2.class.getSimpleName()).singleton).isFalse();
  }

  @Include(BeanConfig004.class)
  interface BeanFactoryMethodCannotHasAnyArgumentsBeanContainer extends BeanContainer {
  }

  @Test(expectedExceptions = FactoryMethodCannotContainAnyArguments.class)
  public void collectFrom_BeanFactoryMethodCannotHasAnyArguments() throws Exception {
    //
    //
    BeanCreationCollector.collectFrom(BeanFactoryMethodCannotHasAnyArgumentsBeanContainer.class);
    //
    //
  }

  @Include(BeanConfig005.class)
  interface BeanFactoryBeanContainer extends BeanContainer {
  }

  @Test
  public void collectFrom_BeanFactory() throws Exception {
    //
    //
    List<BeanCreation> list = BeanCreationCollector.collectFrom(BeanFactoryBeanContainer.class);
    //
    //

    //list.forEach(System.out::println);

    assertThat(list).hasSize(10);

    Map<String, BeanCreation> map = toMapSimple(list);

    BeanCreationWithBeanFactory bean1_creation = (BeanCreationWithBeanFactory) map.get("Bean1");
    BeanCreationWithBeanFactory bean2_creation = (BeanCreationWithBeanFactory) map.get("Bean2");
    BeanCreationWithBeanFactory bean3_creation = (BeanCreationWithBeanFactory) map.get("Bean3");
    BeanCreationWithBeanFactory bean4_creation = (BeanCreationWithBeanFactory) map.get("Bean4");
    BeanCreationWithBeanFactory bean5_creation = (BeanCreationWithBeanFactory) map.get("Bean5");

    assertThat(bean1_creation).isNotNull();
    assertThat(bean2_creation).isNotNull();
    assertThat(bean3_creation).isNotNull();
    assertThat(bean4_creation).isNotNull();
    assertThat(bean5_creation).isNotNull();

    assertThat(bean1_creation.beanFactorySource.targetClass.getName()).isEqualTo(BeanFactory1.class.getName());
    assertThat(bean2_creation.beanFactorySource.targetClass.getName()).isEqualTo(BeanFactory2.class.getName());
    assertThat(bean3_creation.beanFactorySource.targetClass.getName()).isEqualTo(BeanFactory2.class.getName());
    assertThat(bean4_creation.beanFactorySource.targetClass.getName()).isEqualTo(BeanFactory4.class.getName());
    assertThat(bean5_creation.beanFactorySource.targetClass.getName()).isEqualTo(BeanFactory5.class.getName());
  }

  @Include(BeanConfig006.class)
  interface BeanContainer_for_NoDefaultBeanFactory extends BeanContainer {
  }

  @Test(expectedExceptions = NoDefaultBeanFactory.class)
  public void collectFrom_BeanFactory_NoDefaultBeanFactory() throws Exception {
    //
    //
    BeanCreationCollector.collectFrom(BeanContainer_for_NoDefaultBeanFactory.class);
    //
    //
  }

  @Include(BeanConfig012.class)
  interface BeanContainer_WithInterfaceBeanFactoryReference extends BeanContainer {
  }

  @Test
  public void collectFrom_InterfaceBeanFactoryReference() throws Exception {
    //
    //
    BeanCreationCollector.collectFrom(BeanContainer_WithInterfaceBeanFactoryReference.class);
    //
    //
  }

  @Include(BeanConfig013.class)
  interface BeanContainer_WithAbstractBeanFactoryReference extends BeanContainer {
  }

  @Test
  public void collectFrom_AbstractBeanFactoryReference() throws Exception {
    //
    //
    BeanCreationCollector.collectFrom(BeanContainer_WithAbstractBeanFactoryReference.class);
    //
    //
  }

  @Include(BeanConfig014_1.class)
  interface BeanContainer_BeanScannerPackage_withParentReference extends BeanContainer {
  }

  @Test
  public void collectFrom_BeanScannerPackage_withParentReference() throws Exception {
    //
    //
    List<BeanCreation> bcList = BeanCreationCollector
      .collectFrom(BeanContainer_BeanScannerPackage_withParentReference.class);
    //
    //

    assertThat(bcList).hasSize(1);
    assertThat(bcList.get(0).beanClass.getName()).isEqualTo(RemoteBean.class.getName());
  }

  @Include(BeanConfig014_2.class)
  interface BeanContainer_BeanScannerPackage_withRelativeReference extends BeanContainer {
  }

  @Test
  public void collectFrom_BeanScannerPackage_withRelativeReference() throws Exception {
    //
    //
    List<BeanCreation> bcList = BeanCreationCollector
      .collectFrom(BeanContainer_BeanScannerPackage_withRelativeReference.class);
    //
    //

    assertThat(bcList).hasSize(1);
    assertThat(bcList.get(0).beanClass.getName()).isEqualTo(RemoteBean.class.getName());
  }

  @Include(BeanConfig014_3.class)
  interface BeanContainer_BeanScannerPackage_withFullReference extends BeanContainer {
  }

  @Test
  public void collectFrom_BeanScannerPackage_withFullReference() throws Exception {
    //
    //
    List<BeanCreation> bcList = BeanCreationCollector
      .collectFrom(BeanContainer_BeanScannerPackage_withFullReference.class);
    //
    //

    assertThat(bcList).hasSize(1);
    assertThat(bcList.get(0).beanClass.getName()).isEqualTo(RemoteBean.class.getName());
  }
}
