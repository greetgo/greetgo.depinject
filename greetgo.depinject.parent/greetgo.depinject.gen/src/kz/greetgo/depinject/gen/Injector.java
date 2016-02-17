package kz.greetgo.depinject.gen;

import java.io.PrintWriter;
import java.util.List;

public abstract class Injector implements Comparable<Injector> {
  public abstract String compareStr();

  public abstract void intoVariable(PrintWriter writer, String variableName, int spaces);

  public abstract List<BeanDefinition> sourceList();

  @Override
  public int compareTo(@SuppressWarnings("NullableProblems") Injector o) {
    return compareStr().compareTo(o.compareStr());
  }
}
