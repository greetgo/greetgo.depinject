package kz.greetgo.malina;

import java.net.URL;
import java.net.URLClassLoader;

public class Malina {
  public static void printMalina() throws Exception {
    System.out.println("Hello from Malina");

    ClassLoader cl = ClassLoader.getSystemClassLoader();

    URL[] urls = ((URLClassLoader) cl).getURLs();

    for (URL url : urls) {
      System.out.println("Current classpath " + url.getFile());
    }

    System.out.println(Class.forName("kz.greetgo.malina_gen.Sin").newInstance().toString());
  }
}
