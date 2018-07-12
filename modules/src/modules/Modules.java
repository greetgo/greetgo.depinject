package modules;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Modules {

  private static final String ANCHOR_NAME = "modules/anchor.3h54u3h56h3.txt";

  static Path findRoot() {
    if (new File(ANCHOR_NAME).exists()) {
      return Paths.get(".").toAbsolutePath().normalize();
    }

    for (int i = 1; i <= 10; i++) {

      StringBuilder sb = new StringBuilder();
      for (int j = 0; j < i; j++) sb.append("../");

      if (new File(sb.toString() + ANCHOR_NAME).exists()) return Paths.get(sb.toString()).toAbsolutePath().normalize();

    }

    throw new RuntimeException("Cannot find root project dir");
  }

  public static Path depinject() {
    return findRoot().resolve("greetgo.depinject");
  }

  public static Path depinjectGradle() {
    return findRoot().resolve("greetgo.depinject.gradle");
  }
}
