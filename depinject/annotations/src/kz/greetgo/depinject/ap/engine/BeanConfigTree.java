package kz.greetgo.depinject.ap.engine;

import java.util.ArrayList;
import java.util.List;

public class BeanConfigTree {

  public final List<TreeElement> tree = new ArrayList<>();
  public int tab = 0;

  public enum TreeElementType {
    ROOT, Include, ScanPackage, Bean;

    @Override
    public String toString() {
      if (this == Bean) {
        return "+ " + super.toString();
      }
      return super.toString();
    }
  }

  public static class TreeElement {
    final int tab;
    final TreeElementType type;
    final String message;

    public TreeElement(int tab, TreeElementType type, String message) {
      this.tab = tab;
      this.type = type;
      this.message = message;
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (int i = 0, c = tab + 2; i < c; i++) {
        sb.append('\t');
      }
      sb.append(type).append(' ').append(message);
      return sb.toString();
    }
  }

  private void message(TreeElementType type, String message) {
    tree.add(new TreeElement(tab, type, message));
  }

  public void root(String message) {
    message(TreeElementType.ROOT, message);
  }

  public void includes(String message) {
    message(TreeElementType.Include, message);
  }

  public void scannerPackage(String message) {
    message(TreeElementType.ScanPackage, message);
  }

  public void bean(String message) {
    message(TreeElementType.Bean, message);
  }

  public String asStr(boolean showBeans, boolean showTitle) {
    StringBuilder sb = new StringBuilder();
    if (showTitle) {
      sb.append("\nBean Config Tree (without unusable branches):");
    }

    tree.stream()
      .filter(a -> showBeans || a.type != TreeElementType.Bean)
      .forEachOrdered(s -> sb.append('\n').append(s));

    return sb.toString();
  }

}
