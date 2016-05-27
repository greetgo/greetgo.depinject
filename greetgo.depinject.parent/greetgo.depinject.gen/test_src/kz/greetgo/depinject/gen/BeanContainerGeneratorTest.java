package kz.greetgo.depinject.gen;


import kz.greetgo.depinject.core.*;
import kz.greetgo.depinject.gen.beans.MainConfig;
import kz.greetgo.depinject.gen.beans.groupA.*;
import kz.greetgo.depinject.gen.beans.left_factory_method.BeanConfigWithLeftFactoryMethod;
import kz.greetgo.depinject.gen.errors.*;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kz.greetgo.depinject.gen.BeanContainerGenerator.*;
import static org.fest.assertions.api.Assertions.assertThat;

@SuppressWarnings("unused")
public class BeanContainerGeneratorTest {


  @Test(expectedExceptions = NoBeanContainer.class)
  public void collectBeanDefinitionsForBeanContainer_NoBeanContainer() {
    //
    //
    collectBeanDefinitionsForBeanContainer(BeanContainerGeneratorTest.class);
    //
    //
  }

  private void addBeanContainerMethod(List<BeanContainerMethod> list,
                                      Map<Class<?>, BeanDefinition> map, Class<BeanX2> aClass) {
    list.add(new BeanContainerMethod(null, null, map.get(aClass)));
  }

  private interface BeanContainerWithoutInclude extends BeanContainer {
  }


  @Test(expectedExceptions = NoInclude.class)
  public void collectBeanDefinitionsForBeanContainer_NoInclude() {
    //
    //
    collectBeanDefinitionsForBeanContainer(BeanContainerWithoutInclude.class);
    //
    //
  }

  private class LeftBeanConfig {
  }

  @Include({LeftBeanConfig.class})
  private interface BeanContainerWithoutBeanConfig extends BeanContainer {
  }

  @Test(expectedExceptions = NoBeanConfig.class)
  public void collectBeanDefinitionsForBeanContainer_NoBeanConfig() {
    //
    //
    collectBeanDefinitionsForBeanContainer(BeanContainerWithoutBeanConfig.class);
    //
    //
  }


  @Include({BeanConfigWithLeftFactoryMethod.class})
  private interface BeanContainerWithLeftFactoryMethod extends BeanContainer {
  }

  @Test(expectedExceptions = BeanFactoryMethodCannotHasAnyArguments.class)
  public void collectBeanDefinitionsForBeanContainer_BeanFactoryMethodCannotHasAnyArguments() {
    //
    //
    collectBeanDefinitionsForBeanContainer(BeanContainerWithLeftFactoryMethod.class);
    //
    //
  }

  @Include({MainConfig.class})
  private interface TestBeanContainer extends BeanContainer {
  }

  @Test
  public void collectBeanDefinitionsForBeanContainer_simpleBeanDefinition() {

    //
    //
    final Map<Class<?>, BeanDefinition> map = collectBeanDefinitionsForBeanContainer(TestBeanContainer.class);
    //
    //

    assertThat(map).isNotNull();

    assertThat(map).containsKey(BeanA1.class);


    final BeanDefinition beanA1Definition = map.get(BeanA1.class);
    assertThat(beanA1Definition.beanClass.getName()).isEqualTo(BeanA1.class.getName());
    assertThat(beanA1Definition.singleton).isTrue();
    assertThat(beanA1Definition.beanClassFactory).isNull();
    assertThat(beanA1Definition.factoryMethod).isNull();

    assertThat(map).containsKey(BeanA2.class);

    final BeanDefinition beanA2Definition = map.get(BeanA2.class);
    assertThat(beanA2Definition.beanClass.getName()).isEqualTo(BeanA2.class.getName());
    assertThat(beanA2Definition.singleton).isFalse();
    assertThat(beanA2Definition.beanClassFactory).isNull();
    assertThat(beanA2Definition.factoryMethod).isNull();
  }

  @Test
  public void collectBeanDefinitionsForBeanContainer_factoryBeanDefinition() {

    //
    //
    final Map<Class<?>, BeanDefinition> map = collectBeanDefinitionsForBeanContainer(TestBeanContainer.class);
    //
    //

    assertThat(map).isNotNull();

    assertThat(map).containsKey(FactoredBean1.class);

    final BeanDefinition factoredBean1Definition = map.get(FactoredBean1.class);
    assertThat(factoredBean1Definition.beanClass.getName()).isEqualTo(FactoredBean1.class.getName());
    assertThat(factoredBean1Definition.singleton).isTrue();
    assertThat(factoredBean1Definition.beanClassFactory.getName()).isEqualTo(BeanA1.class.getName());
    assertThat(factoredBean1Definition.factoryMethod.getName()).isEqualTo("createFactoredBean1");

    assertThat(map).containsKey(FactoredBean2.class);

    final BeanDefinition factoredBean2Definition = map.get(FactoredBean2.class);
    assertThat(factoredBean2Definition.beanClass.getName()).isEqualTo(FactoredBean2.class.getName());
    assertThat(factoredBean2Definition.singleton).isFalse();
    assertThat(factoredBean2Definition.beanClassFactory.getName()).isEqualTo(BeanA1.class.getName());
    assertThat(factoredBean2Definition.factoryMethod.getName()).isEqualTo("createFactoredBean2");

    assertThat(map).containsKey(FactoredBean3.class);

    final BeanDefinition factoredBean3Definition = map.get(FactoredBean3.class);
    assertThat(factoredBean3Definition.beanClass.getName()).isEqualTo(FactoredBean3.class.getName());
    assertThat(factoredBean3Definition.singleton).isFalse();
    assertThat(factoredBean3Definition.beanClassFactory.getName()).isEqualTo(BeanA2.class.getName());
    assertThat(factoredBean3Definition.factoryMethod.getName()).isEqualTo("createFactoredBean3");
  }

  private static void addBeanDefinition(Map<Class<?>, BeanDefinition> map, Class<?> beanClass) {
    addBeanDefinition(map, beanClass, null);
  }

  private static void addBeanDefinition(Map<Class<?>, BeanDefinition> map, Class<?> beanClass, Class<?> factoryBeanClass) {
    final BeanDefinition beanDefinition = new BeanDefinition(beanClass, true, factoryBeanClass, null,
        kz.greetgo.depinject.core.BeanFactory.class);
    map.put(beanDefinition.beanClass, beanDefinition);
  }

  private interface ToBeanInterface {
  }

  private class ToBean implements ToBeanInterface {
    public BeanGetter<ToBean> fieldB;
    public BeanGetter<ToBean> fieldA;
  }

  private class SourceBean {
    public BeanGetter<ToBeanInterface> fieldToBean;
  }

  @Test
  public void initBeanDefinitions_injectors() {
    final Map<Class<?>, BeanDefinition> map = new HashMap<>();

    addBeanDefinition(map, ToBean.class);
    addBeanDefinition(map, SourceBean.class);

    //
    //
    initBeanDefinitions(map);
    //
    //

    {
      final List<Injector> injectors = map.get(SourceBean.class).injectors;
      assertThat(injectors).hasSize(1);
      assertThat(injectors.get(0)).isInstanceOf(InjectorSingle.class);
      InjectorSingle i = (InjectorSingle) injectors.get(0);
      assertThat(i.field.getName()).isEqualTo("fieldToBean");
      assertThat(i.owner).isEqualTo(map.get(SourceBean.class));
      assertThat(i.source).isEqualTo(map.get(ToBean.class));
    }
    {
      final List<Injector> injectors = map.get(ToBean.class).injectors;
      assertThat(injectors).hasSize(2);
      assertThat(injectors.get(0)).isInstanceOf(InjectorSingle.class);
      assertThat(injectors.get(1)).isInstanceOf(InjectorSingle.class);
      InjectorSingle i0 = (InjectorSingle) injectors.get(0);
      InjectorSingle i1 = (InjectorSingle) injectors.get(1);
      assertThat(i0.field.getName()).isEqualTo("fieldA");
      assertThat(i1.field.getName()).isEqualTo("fieldB");
    }
  }

  interface PreparingFactor {
  }

  private class PreparingBean implements PreparingFactor {
  }

  private class NotPreparingBean {
  }


  private abstract class PreparationBean implements BeanPreparation<PreparingFactor> {
  }

  interface PreparingFactor2 {
  }

  abstract class ParentPreparation implements BeanPreparation<PreparingFactor2> {
  }

  private abstract class PreparationBean2 extends ParentPreparation {
  }

  private interface IParentPreparation extends BeanPreparation<PreparingFactor2> {
  }

  abstract class PreparationBean3 implements IParentPreparation {
  }

  private abstract class PreparationBean4 extends PreparationBean3 {
  }

  @Test
  public void initBeanDefinitions_preparations() {
    final Map<Class<?>, BeanDefinition> map = new HashMap<>();

    addBeanDefinition(map, PreparingBean.class);
    addBeanDefinition(map, PreparationBean.class);
    addBeanDefinition(map, NotPreparingBean.class);
    addBeanDefinition(map, PreparationBean2.class);
    addBeanDefinition(map, PreparationBean3.class);
    addBeanDefinition(map, PreparationBean4.class);

    //
    //
    initBeanDefinitions(map);
    //
    //

    final BeanDefinition preparationBeanDefinition = map.get(PreparationBean.class);
    final BeanDefinition preparingBeanDefinition = map.get(PreparingBean.class);
    final BeanDefinition notPreparingBeanDefinition = map.get(NotPreparingBean.class);

    assertThat(preparationBeanDefinition.prepareReferenceClass).isNotNull();
    assertThat(preparationBeanDefinition.prepareReferenceClass.getName()).isEqualTo(PreparingFactor.class.getName());
    assertThat(preparationBeanDefinition.preparingBy).isEmpty();

    assertThat(preparingBeanDefinition.prepareReferenceClass).isNull();
    assertThat(preparingBeanDefinition.preparingBy).hasSize(1);
    assertThat(preparingBeanDefinition.preparingBy.get(0).beanClass.getName())
        .isEqualTo(PreparationBean.class.getName());

    assertThat(notPreparingBeanDefinition.prepareReferenceClass).isNull();
    assertThat(notPreparingBeanDefinition.preparingBy).hasSize(0);

    final BeanDefinition preparationBeanDefinition2 = map.get(PreparationBean2.class);

    assertThat(preparationBeanDefinition2.prepareReferenceClass).isNotNull();
    assertThat(preparationBeanDefinition2.prepareReferenceClass.getName()).isEqualTo(PreparingFactor2.class.getName());
    assertThat(preparationBeanDefinition2.preparingBy).isEmpty();

    final BeanDefinition preparationBeanDefinition3 = map.get(PreparationBean3.class);

    assertThat(preparationBeanDefinition3.prepareReferenceClass).isNotNull();
    assertThat(preparationBeanDefinition3.prepareReferenceClass.getName()).isEqualTo(PreparingFactor2.class.getName());
    assertThat(preparationBeanDefinition3.preparingBy).isEmpty();

    final BeanDefinition preparationBeanDefinition4 = map.get(PreparationBean4.class);

    assertThat(preparationBeanDefinition4.prepareReferenceClass).isNotNull();
    assertThat(preparationBeanDefinition4.prepareReferenceClass.getName()).isEqualTo(PreparingFactor2.class.getName());
    assertThat(preparationBeanDefinition4.preparingBy).isEmpty();
  }

  private class BeanFactory {
  }

  private class BeanA {
  }

  private class BeanB {
    public BeanGetter<BeanA> a;
  }

  @Test
  public void initBeanDefinitions_using() {
    final Map<Class<?>, BeanDefinition> map = new HashMap<>();

    addBeanDefinition(map, BeanFactory.class);
    addBeanDefinition(map, BeanA.class, BeanFactory.class);
    addBeanDefinition(map, BeanB.class);

    //
    //
    initBeanDefinitions(map);
    //
    //

    final BeanDefinition beanDefinitionFactory = map.get(BeanFactory.class);

    final BeanDefinition beanDefinitionA = map.get(BeanA.class);

    assertThat(beanDefinitionA.using).containsOnly(beanDefinitionFactory);

    final BeanDefinition beanDefinitionB = map.get(BeanB.class);

    assertThat(beanDefinitionB.using).containsOnly(beanDefinitionA);

    assertThat(beanDefinitionFactory.using).isEmpty();
  }

  private abstract class IniBean implements HasAfterInject {
  }

  private class NoIniBean {
  }

  @Test
  public void initBeanDefinitions_hasAfterInject() {
    final Map<Class<?>, BeanDefinition> map = new HashMap<>();

    addBeanDefinition(map, IniBean.class);
    addBeanDefinition(map, NoIniBean.class);

    //
    //
    initBeanDefinitions(map);
    //
    //

    {
      final BeanDefinition beanDefinition = map.get(NoIniBean.class);
      assertThat(beanDefinition.hasAfterInject).isFalse();
    }
    {
      final BeanDefinition beanDefinition = map.get(IniBean.class);
      assertThat(beanDefinition.hasAfterInject).isTrue();
    }
  }

  interface TmpInterface {
  }

  private abstract class TestPreparation implements BeanPreparation<TmpInterface> {
  }

  private class PreparingTestBean implements TmpInterface {
  }

  @Test
  public void initBeanDefinitions_using_preparation() {
    final Map<Class<?>, BeanDefinition> map = new HashMap<>();

    addBeanDefinition(map, PreparingTestBean.class);
    addBeanDefinition(map, TestPreparation.class);

    //
    //
    initBeanDefinitions(map);
    //
    //

    final BeanDefinition preparingTestBeanDefinition = map.get(PreparingTestBean.class);
    final BeanDefinition testPreparationDefinition = map.get(TestPreparation.class);

    assertThat(preparingTestBeanDefinition.using).containsOnly(testPreparationDefinition);
  }

  private interface BeanContainerErrorTest {
    BeanX asd(int x);
  }

  @Test(expectedExceptions = BeanContainerMethodCannotContainsAnyArguments.class)
  public void collectBeanContainerMethods_BeanContainerMethodCannotContainsAnyArguments() {

    //
    //
    collectBeanContainerMethods(BeanContainerErrorTest.class, null);
    //
    //

  }

  interface BeanXInterface {
  }

  class BeanX implements BeanXInterface {
  }

  private interface BeanContainerTest {
    BeanXInterface getBeanXName();
  }

  @Test
  public void collectBeanContainerMethods_ok() {

    final Map<Class<?>, BeanDefinition> map = new HashMap<>();

    addBeanDefinition(map, BeanX.class);

    //
    //
    List<BeanContainerMethod> list = collectBeanContainerMethods(BeanContainerTest.class, map);
    //
    //

    assertThat(list).hasSize(1);
    assertThat(list.get(0).name).isEqualTo("getBeanXName");
    assertThat(list.get(0).referenceType.toString()).isEqualTo(BeanXInterface.class.toString());
    assertThat(list.get(0).beanDefinition.beanClass.getName()).isEqualTo(BeanX.class.getName());
  }

  private interface BeanContainerForSortTest {
    BeanXInterface getB();

    BeanXInterface getZ();

    BeanXInterface getU();

    BeanXInterface getA();
  }

  @Test
  public void collectBeanContainerMethods_sort() {

    final Map<Class<?>, BeanDefinition> map = new HashMap<>();

    addBeanDefinition(map, BeanX.class);

    //
    //
    List<BeanContainerMethod> list = collectBeanContainerMethods(BeanContainerForSortTest.class, map);
    //
    //

    assertThat(list).hasSize(4);
    assertThat(list.get(0).name).isEqualTo("getA");
    assertThat(list.get(1).name).isEqualTo("getB");
    assertThat(list.get(2).name).isEqualTo("getU");
    assertThat(list.get(3).name).isEqualTo("getZ");
  }

  private class BeanX1 {

  }

  private class BeanX2 {
    public BeanGetter<BeanX1> x1;
  }


  private class BeanX3 {
    public BeanGetter<BeanX2> x2;
  }

  @Test
  public void collectUsedMap_ok() {
    final Map<Class<?>, BeanDefinition> map = new HashMap<>();

    addBeanDefinition(map, BeanX1.class);
    addBeanDefinition(map, BeanX2.class);
    addBeanDefinition(map, BeanX3.class);
    initBeanDefinitions(map);

    List<BeanContainerMethod> list = new ArrayList<>();

    addBeanContainerMethod(list, map, BeanX2.class);

    //
    //
    final Map<Class<?>, BeanDefinition> usedMap = collectUsedMap(list);
    //
    //

    assertThat(usedMap).hasSize(2);
    assertThat(usedMap.keySet()).containsOnly(BeanX1.class, BeanX2.class);
  }

}