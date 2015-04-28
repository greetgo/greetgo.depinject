package kz.greetgo.depinject.gen;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import kz.greetgo.depinject.gen.scanner.ClassScanner;
import kz.greetgo.depinject.gen.scanner.ClassScannerDef;
import kz.greetgo.depinject.src.Bean;
import kz.greetgo.depinject.src.BeanConfig;
import kz.greetgo.depinject.src.BeanContainer;
import kz.greetgo.depinject.src.Include;
import kz.greetgo.depinject.src.ScanBeans;

public class BeanContainerImplGenerator {
  private final ClassScanner classScanner = new ClassScannerDef();
  
  public Class<?> beanContainerIface;
  
  final Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
  
  private final Set<Class<?>> scannedConfigs = new HashSet<Class<?>>();
  
  void findBeanClassSet() throws IOException {
    if (!beanContainerIface.isInterface()) throw new NoInterface();
    if (!BeanContainer.class.isAssignableFrom(beanContainerIface)) throw new NoBeanContainer();
    
    Include include = beanContainerIface.getAnnotation(Include.class);
    if (include == null) throw new NoInclude();
    
    scannedConfigs.clear();
    
    for (Class<?> config : include.value()) {
      appendAllBeansFromConfig(config);
    }
    
  }
  
  private void appendAllBeansFromConfig(Class<?> config) throws IOException {
    BeanConfig beanConfig = config.getAnnotation(BeanConfig.class);
    if (beanConfig == null) throw new NoBeanConfig();
    
    if (scannedConfigs.contains(config)) return;
    scannedConfigs.add(config);
    
    ScanBeans scanBeans = config.getAnnotation(ScanBeans.class);
    if (scanBeans != null) {
      for (String subpath : scanBeans.value()) {
        scanPathForBeans(config, subpath);
      }
    }
    
    Include include = config.getAnnotation(Include.class);
    if (include != null) {
      for (Class<?> subconfig : include.value()) {
        appendAllBeansFromConfig(subconfig);
      }
    }
    
  }
  
  private void scanPathForBeans(Class<?> config, String subpath) throws IOException {
    String packageName = config.getPackage().getName();
    String scanPackageName = applySubpath(packageName, subpath);
    for (Class<?> cl : classScanner.scanPackage(scanPackageName)) {
      if (cl.getAnnotation(Bean.class) != null) {
        beanClassSet.add(cl);
      }
    }
  }
  
  private String applySubpath(String packageName, String subpath) {
    if (subpath == null) return packageName;
    if (subpath.length() == 0) return packageName;
    if (".".equals(subpath)) return packageName;
    if (subpath.startsWith(".")) {
      subpath = subpath.substring(1);
    }
    return packageName + '.' + subpath;
  }
}
