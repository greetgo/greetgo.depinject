package kz.greetgo.depinject.gen2;

import java.io.PrintStream;

public class Tab {
  public static void tab(int tab, PrintStream out) {
    for (int i = 0; i < tab; i++) {
      out.print("  ");
    }
  }

  public static String tab(int tab) {
    StringBuilder sb = new StringBuilder(tab * 2);
    for (int i = 0; i < tab; i++) {
      sb.append("  ");
    }
    return sb.toString();
  }
}
