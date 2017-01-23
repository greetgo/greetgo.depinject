package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanPreparation;
import kz.greetgo.depinject.gen.errors.NoMethodsInBeanContainer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanContainerManager {
  private final Context context;
  private final Class<?> beanContainerInterface;

  BeanContainerManager(Context context, Class<?> beanContainerInterface) {
    this.context = context;
    this.beanContainerInterface = beanContainerInterface;
  }

  BeanCreationCollector collector;

  List<BeanContainerMethod> beanContainerMethodList;
  List<BeanCreation> beanCreationList;
  List<BeanReference> allBeanReferences;
  List<BeanCreation> preparations, replacers;

  List<BeanCreation> usingBeanCreationList;
  List<BeanReference> usingBeanReferences;

  List<GetterCreation> writingGetterCreations;

  List<BeanReference> writingBeanReferences;

  void prepareToWrite() {

    //
    // PREPARE REFERENCES
    //

    beanContainerMethodList = context.extractBeanContainerMethodList(beanContainerInterface);

    collector = context.newBeanCreationCollector(beanContainerInterface);
    collector.collect();

    beanCreationList = collector.beanCreationList
      .stream().unordered().distinct().collect(Collectors.toList());
    beanCreationList.sort(Comparator.comparing(o -> o.beanClass.getName()));

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

    replacers = beanCreationList.stream()
      .peek(BeanCreation::calculateReplaceChecker)
      .filter(BeanCreation::hasReplaceChecker)
      .peek(BeanCreation::calculateReplacerPriority)
      .sorted(Comparator.comparing(BeanCreation::replacerPriority))
      .collect(Collectors.toList());

    allBeanReferences.forEach(r -> r.useReplacers(replacers));

    //
    // MARK TO USE
    //

    beanContainerMethodList.forEach(BeanContainerMethod::markToUse);

    //
    // INIT USING AND CHECK CONNECTIVITY
    //

    usingBeanCreationList = beanCreationList.stream().filter(a -> a.use).collect(Collectors.toList());
    usingBeanReferences = allBeanReferences.stream().filter(a -> a.use).collect(Collectors.toList());

    usingBeanReferences.forEach(BeanReference::checkConnectivity);

    Map<GetterCreation, List<GetterCreation>> getterCreationMap = new HashMap<>();
    usingBeanReferences.stream().flatMap(a -> a.getterCreations.stream())
      .filter(GetterCreation::needGetter)
      .forEachOrdered(a -> getterCreationMap.computeIfAbsent(a, k -> new ArrayList<>()).add(a));

    writingGetterCreations = new ArrayList<>();
    writingGetterCreations.addAll(getterCreationMap.keySet());
    writingGetterCreations.sort(Comparator.comparing(o -> o.beanCreation.beanClass.getName()));

    Map<BeanReference, List<BeanReference>> beanReferenceMap = new HashMap<>();
    usingBeanReferences.stream()
      .filter(BeanReference::needGetter)
      .forEachOrdered(br -> beanReferenceMap.computeIfAbsent(br, k -> new ArrayList<>()).add(br));

    writingBeanReferences = beanReferenceMap.keySet().stream().collect(Collectors.toList());
    writingBeanReferences.sort(Comparator.comparing(BeanReference::compareStr));

    //
    // INDEXING OF VARIABLES ...
    //

    int varIndex[] = {1};

    usingBeanCreationList.forEach(bc -> bc.varIndex = varIndex[0]++);

    writingBeanReferences.forEach(br -> {
      br.varIndex = varIndex[0]++;
      beanReferenceMap.get(br).forEach(br2 -> br2.varIndex = br.varIndex);
    });

    writingGetterCreations.forEach(gc -> {
      gc.varIndex = varIndex[0]++;
      getterCreationMap.get(gc).forEach(gc2 -> gc2.varIndex = gc.varIndex);
    });

    //
    // THE END OF PREPARATION
    //

  }

  void writeBeanContainerMethods(int tab, Outer out) {
    if (beanContainerMethodList.isEmpty()) throw new NoMethodsInBeanContainer(beanContainerInterface);
    beanContainerMethodList.forEach(bcm -> bcm.writeBeanContainerMethod(tab, out));
  }

  @SuppressWarnings("SameParameterValue")
  void writeBeanCreations(int tab, Outer out) {
    usingBeanCreationList.forEach(a -> a.writeGetter(tab, out));
  }

  void writeBeanContainerImpl0(Outer outer, String packageName, String classSimpleName) {

    if (packageName != null) {
      outer.stn("package " + packageName + ";");
    }

    outer.stn("public final class " + classSimpleName
      + " implements " + beanContainerInterface.getName().replaceAll("\\$", ".") + '{');

    outer.nl().tab(1).stn("private final java.lang.Object " + Const.SYNC_FIELD + " = new java.lang.Object();");

    outer.nl();
    outer.tab(1).stn("//");
    outer.tab(1).stn("// Bean container methods");
    outer.tab(1).stn("//");

    writeBeanContainerMethods(1, outer);

    outer.nl();
    outer.tab(1).stn("//");
    outer.tab(1).stn("// Bean creations");
    outer.tab(1).stn("//");

    writeBeanCreations(1, outer);

    outer.nl();
    outer.tab(1).stn("//");
    outer.tab(1).stn("// Bean references");
    outer.tab(1).stn("//");

    writeBeanReferences(1, outer);

    outer.tab(1).nl();
    outer.tab(1).stn("//");
    outer.tab(1).stn("// Getter creations");
    outer.tab(1).stn("//");

    writeGetterCreations(1, outer);

    outer.stn("}");
  }


  @SuppressWarnings("SameParameterValue")
  private void writeBeanReferences(int tab, Outer outer) {
    writingBeanReferences.forEach(a -> a.writeGetter(tab, outer));
  }

  @SuppressWarnings("SameParameterValue")
  private void writeGetterCreations(int tab, Outer outer) {
    writingGetterCreations.forEach(gc -> gc.writeGetter(tab, outer));
  }

  public void writeBeanContainerImpl(Outer outer, String packageName, String classSimpleName) {
    prepareToWrite();
    writeBeanContainerImpl0(outer, packageName, classSimpleName);
  }
}
