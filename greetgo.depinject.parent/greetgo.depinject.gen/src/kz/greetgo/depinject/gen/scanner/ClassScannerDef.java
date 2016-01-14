package kz.greetgo.depinject.gen.scanner;

import com.metapossum.utils.scanner.reflect.ClassesInPackageScanner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Реализация сканера по умолчанию
 *
 * @author pompei
 */
public class ClassScannerDef implements ClassScanner {

  private final Map<String, Set<Class<?>>> cache = new HashMap<>();

  @Override
  public Set<Class<?>> scanPackage(final String packageName) {
    {
      final Set<Class<?>> cached = cache.get(packageName);
      if (cached != null) return cached;
    }

    try {
      final Set<Class<?>> ret = new ClassesInPackageScanner().scan(packageName);
      cache.put(packageName, ret);
      return ret;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
