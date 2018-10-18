package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.errors.FactoryMethodCannotContainAnyArguments;
import kz.greetgo.depinject.gen.errors.NoBeanConfig;
import kz.greetgo.depinject.gen.errors.NoBeanContainer;
import kz.greetgo.depinject.gen.errors.NoDefaultBeanFactory;
import kz.greetgo.depinject.gen.errors.NoInclude;
import kz.greetgo.depinject.gen.test_beans001.BeanConfig001;
import kz.greetgo.depinject.gen.test_beans002.BeanConfig002;
import kz.greetgo.depinject.gen.test_beans003.Bean1;
import kz.greetgo.depinject.gen.test_beans003.Bean2;
import kz.greetgo.depinject.gen.test_beans003.BeanConfig003;
import kz.greetgo.depinject.gen.test_beans003.BeanFactory;
import kz.greetgo.depinject.gen.test_beans004.BeanConfig004;
import kz.greetgo.depinject.gen.test_beans005.sub_beans_1.BeanFactory1;
import kz.greetgo.depinject.gen.test_beans005.sub_beans_2.BeanFactory2;
import kz.greetgo.depinject.gen.test_beans005.sub_beans_4.BeanFactory4;
import kz.greetgo.depinject.gen.test_beans005.sub_beans_5.BeanFactory5;
import kz.greetgo.depinject.gen.test_beans005.top.BeanConfig005;
import kz.greetgo.depinject.gen.test_beans006.BeanConfig006;
import kz.greetgo.depinject.gen.test_beans012.BeanConfig012;
import kz.greetgo.depinject.gen.test_beans013.BeanConfig013;
import kz.greetgo.depinject.gen.test_beans014.BeanConfig014_2;
import kz.greetgo.depinject.gen.test_beans014.BeanConfig014_3;
import kz.greetgo.depinject.gen.test_beans014.local_package.sub.BeanConfig014_1;
import kz.greetgo.depinject.gen.test_beans014.remote_package.sub.RemoteBean;
import kz.greetgo.depinject.gen.test_beans029.BeanConfig029;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class BeanCreationCollectorTest {

  public interface BeanContainerWithoutNoBeanContainer {}

  @Test(expectedExceptions = NoBeanContainer.class)
  public void collectFrom_NoBeanContainer() {
    Context context = new Context();

    //
    //
    context.newBeanCreationCollector(BeanContainerWithoutNoBeanContainer.class).collect();
    //
    //
  }

  public interface BeanContainerWithoutInclude extends BeanContainer {
  }

  @Test(expectedExceptions = NoInclude.class)
  public void collectFrom_NoInclude() {
    Context context = new Context();

    //
    //
    context.newBeanCreationCollector(BeanContainerWithoutInclude.class).collect();
    //
    //
  }

  class BeanConfigWithoutBeanConfig {
  }

  @Include(BeanConfigWithoutBeanConfig.class)
  public interface ForNoBeanConfigError extends BeanContainer {
  }

  @Test(expectedExceptions = NoBeanConfig.class)
  public void collectFrom_NoBeanConfig() {
    Context context = new Context();
    //
    //
    context.newBeanCreationCollector(ForNoBeanConfigError.class).collect();
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
  public void collectFrom_BeanWithDefaultConstructor() {
    Context context = new Context();
    //
    //
    List<BeanCreation> list = context.newBeanCreationCollector(HasBeanWithDefaultConstructor.class).collect();
    //
    //

    assertThat(list).hasSize(2);

    Map<String, BeanCreation> map = toMapSimple(list);

    assertThat(map.get("BeanWithDefaultConstructor1")).isInstanceOf(BeanCreationWithConstructor.class);
    assertThat(map.get("BeanWithDefaultConstructor2")).isInstanceOf(BeanCreationWithConstructor.class);

    assertThat(map.get("BeanWithDefaultConstructor1").singleton).isTrue();
    assertThat(map.get("BeanWithDefaultConstructor2").singleton).isFalse();
  }

  @Include(BeanConfig002.class)
  interface WithoutBeanScanner extends BeanContainer {
  }

  @Test
  public void collectFrom_withoutBeanScanner() {
    Context context = new Context();
    //
    //
    List<BeanCreation> list = context.newBeanCreationCollector(WithoutBeanScanner.class).collect();
    //
    //

    assertThat(list).isEmpty();
  }

  @Include(BeanConfig003.class)
  interface FactoryMethodBeanContainer extends BeanContainer {
  }

  @Test
  public void collectFrom_factoryMethod() {
    Context context = new Context();
    //
    //
    List<BeanCreation> list = context.newBeanCreationCollector(FactoryMethodBeanContainer.class).collect();
    //
    //

    assertThat(list).hasSize(3);

    Map<String, BeanCreation> map = toMapSimple(list);

    assertThat(map.get(BeanFactory.class.getSimpleName())).isInstanceOf(BeanCreationWithConstructor.class);
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
  public void collectFrom_BeanFactoryMethodCannotHasAnyArguments() {
    Context context = new Context();
    //
    //
    context.newBeanCreationCollector(BeanFactoryMethodCannotHasAnyArgumentsBeanContainer.class).collect();
    //
    //
  }

  @Include(BeanConfig005.class)
  interface BeanFactoryBeanContainer extends BeanContainer {
  }

  @Test
  public void collectFrom_BeanFactory() {
    Context context = new Context();
    //
    //
    List<BeanCreation> list = context.newBeanCreationCollector(BeanFactoryBeanContainer.class).collect();
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

    assertThat(bean1_creation.beanFactorySource.sourceClass.getName()).isEqualTo(BeanFactory1.class.getName());
    assertThat(bean2_creation.beanFactorySource.sourceClass.getName()).isEqualTo(BeanFactory2.class.getName());
    assertThat(bean3_creation.beanFactorySource.sourceClass.getName()).isEqualTo(BeanFactory2.class.getName());
    assertThat(bean4_creation.beanFactorySource.sourceClass.getName()).isEqualTo(BeanFactory4.class.getName());
    assertThat(bean5_creation.beanFactorySource.sourceClass.getName()).isEqualTo(BeanFactory5.class.getName());
  }

  @Include(BeanConfig006.class)
  interface BeanContainer_for_NoDefaultBeanFactory extends BeanContainer {
  }

  @Test(expectedExceptions = NoDefaultBeanFactory.class)
  public void collectFrom_BeanFactory_NoDefaultBeanFactory() {
    Context context = new Context();
    //
    //
    context.newBeanCreationCollector(BeanContainer_for_NoDefaultBeanFactory.class).collect();
    //
    //
  }

  @Include(BeanConfig012.class)
  interface BeanContainer_WithInterfaceBeanFactoryReference extends BeanContainer {
  }

  @Test
  public void collectFrom_InterfaceBeanFactoryReference() {
    Context context = new Context();
    //
    //
    context.newBeanCreationCollector(BeanContainer_WithInterfaceBeanFactoryReference.class).collect();
    //
    //
  }

  @Include(BeanConfig013.class)
  interface BeanContainer_WithAbstractBeanFactoryReference extends BeanContainer {
  }

  @Test
  public void collectFrom_AbstractBeanFactoryReference() {
    Context context = new Context();
    //
    //
    context.newBeanCreationCollector(BeanContainer_WithAbstractBeanFactoryReference.class).collect();
    //
    //
  }

  @Include(BeanConfig014_1.class)
  interface BeanContainer_BeanScannerPackage_withParentReference extends BeanContainer {
  }

  @Test
  public void collectFrom_BeanScannerPackage_withParentReference() {
    Context context = new Context();
    //
    //
    List<BeanCreation> bcList = context.newBeanCreationCollector(
        BeanContainer_BeanScannerPackage_withParentReference.class).collect();
    //
    //

    assertThat(bcList).hasSize(1);
    assertThat(bcList.get(0).beanClass.getName()).isEqualTo(RemoteBean.class.getName());
  }

  @Include(BeanConfig014_2.class)
  interface BeanContainer_BeanScannerPackage_withRelativeReference extends BeanContainer {
  }

  @Test
  public void collectFrom_BeanScannerPackage_withRelativeReference() {
    Context context = new Context();
    //
    //
    List<BeanCreation> bcList = context.newBeanCreationCollector(
        BeanContainer_BeanScannerPackage_withRelativeReference.class).collect();
    //
    //

    assertThat(bcList).hasSize(1);
    assertThat(bcList.get(0).beanClass.getName()).isEqualTo(RemoteBean.class.getName());
  }

  @Include(BeanConfig014_3.class)
  interface BeanContainer_BeanScannerPackage_withFullReference extends BeanContainer {
  }

  @Test
  public void collectFrom_BeanScannerPackage_withFullReference() {
    Context context = new Context();
    //
    //
    List<BeanCreation> bcList = context.newBeanCreationCollector(
        BeanContainer_BeanScannerPackage_withFullReference.class).collect();
    //
    //

    assertThat(bcList).hasSize(1);
    assertThat(bcList.get(0).beanClass.getName()).isEqualTo(RemoteBean.class.getName());
  }

  @DataProvider
  public Object[][] calcFullName_DP() {
    //noinspection SpellCheckingInspection
    return new Object[][]{
        {"kz.greetgo.depinject.gen", "com.google.sap", "com.google.sap"},
        {"kz.greetgo.depinject.gen", ".hello.world", "kz.greetgo.depinject.gen.hello.world"},
        {"kz.greetgo.depinject.gen", "^.by.world", "kz.greetgo.depinject.by.world"},
        {"kz.greetgo.depinject.gen", "^^.gcory.pink.world", "kz.greetgo.gcory.pink.world"},
        {"kz.greetgo.depinject.gen", "^^^.greetCom.gcory.pink.world", "kz.greetCom.gcory.pink.world"},
        {"kz.greetgo.depinject.gen", "^by.world", "kz.greetgo.depinject.by.world"},
        {"kz.greetgo.depinject.gen", "^^gcory.pink.world", "kz.greetgo.gcory.pink.world"},
        {"kz.greetgo.depinject.gen", "^^^greetCom.gcory.pink.world", "kz.greetCom.gcory.pink.world"},
    };
  }

  @Test(dataProvider = "calcFullName_DP")
  public void calcFullName(String current, String relative, String expected) {

    //
    //
    String actual = BeanCreationCollector.calcFullName(current, relative);
    //
    //

    assertThat(actual).isEqualTo(expected);

  }

  @Include(BeanConfig029.class)
  interface BeanContainer029 extends BeanContainer {}

  @Test
  public void collectAndViewBeanConfigTree() {
    Context context = new Context();
    BeanCreationCollector collector = context.newBeanCreationCollector(BeanContainer029.class);
    collector.collect();

    System.out.println(context.configTree.asStr(false, true));
  }
}
