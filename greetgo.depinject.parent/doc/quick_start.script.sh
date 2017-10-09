 #!/usr/bin/env bash
 #                                                                                                 ▲
 # Надо скопировать весь текст этого файла как есть и вставить его в терминал с bash-ем, для этого:│
 # 1. надо нажать на кнопку Raw здесь ─────────────────────────────────────────────────────────────┘
 # 2. нажать Ctrl+A потом нажать Ctrl+Ins
 # 3. перенести фокус в терминал с bash-ем
 # 4. нажать Shift+Ins

 rm -rf depinject.quick_start
 mkdir depinject.quick_start
 cd depinject.quick_start

 mkdir -p src/main/java/depinject/quick_start/beans
 mkdir -p src/main/java/depinject/quick_start/launcher
 mkdir -p src/test/java/depinject/quick_start/gen

 cat > build.gradle <<'EOF'
apply plugin: 'java'
apply plugin: 'maven'
apply plugin: 'idea'

repositories {
  jcenter()
}

dependencies {
  ext.depinjectVersion = '2.0.0'

  //Эта либа очень маленькая и не содержит зависимостей
  compile "kz.greetgo.depinject:greetgo.depinject:$depinjectVersion"

  //Эта нужна для генерации реализации BeanConfig-ов - в продакшн она не идёт
  testCompile "kz.greetgo.depinject:greetgo.depinject.gen:$depinjectVersion"
}

task generateBeanContainers(
  type: JavaExec,
  description: 'Эта таска генерирует реализацию BeanContainer-ов и компилирует их'
) {
  ext.genDir = "${project.buildDir}/generated/bean_container_impl"

  main = 'depinject.quick_start.gen.GenerateAndCompileBeanContainers'
  args = ["${genDir}"]

  classpath { [sourceSets.test.runtimeClasspath,] }
}

task beanContainerJar(
  type: Jar,
  description: 'Эта таска генерирует jar-файл с исходниками реализации BeanContainer-ов'
) {
  dependsOn generateBeanContainers
  baseName "bean-containers"
  from generateBeanContainers.genDir
}

//Тут генерируется основной JAR-файл
jar {
  dependsOn beanContainerJar

  manifest {
    attributes "Main-Class": "depinject.quick_start.launcher.MainLauncher"
  }

  //Собрать все зависимости в одном файле
  from {
    configurations.compile.collect { it.isDirectory() ? it : zipTree(it) }
  }

  //Добавить к основному JAR-у реализацию BeanContainer-ов
  with beanContainerJar
}
EOF

 cat > src/main/java/depinject/quick_start/beans/BeanConfigHelloWorld.java <<'EOF'
package depinject.quick_start.beans;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;

@BeanConfig
@BeanScanner
public class BeanConfigHelloWorld {}
EOF

 cat > src/main/java/depinject/quick_start/beans/Hello.java <<'EOF'
package depinject.quick_start.beans;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Hello {
  public String message() {
    return "Hello";
  }
}
EOF

 cat > src/main/java/depinject/quick_start/beans/World.java <<'EOF'
package depinject.quick_start.beans;

import kz.greetgo.depinject.core.Bean;

@Bean
public class World {
  public String message() {
    return "World";
  }
}
EOF

 cat > src/main/java/depinject/quick_start/beans/HelloWorld.java <<'EOF'
package depinject.quick_start.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class HelloWorld {

  public BeanGetter<Hello> hello;
  public BeanGetter<World> world;

  public String message() {
    return hello.get().message() + ' ' + world.get().message() + "!!!";
  }
}
EOF

 cat > src/main/java/depinject/quick_start/launcher/MainBeanContainer.java <<'EOF'
package depinject.quick_start.launcher;

import depinject.quick_start.beans.BeanConfigHelloWorld;
import depinject.quick_start.beans.HelloWorld;
import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;

@Include(BeanConfigHelloWorld.class)
public interface MainBeanContainer extends BeanContainer {
  HelloWorld getHelloWorld();
}
EOF

 cat > src/main/java/depinject/quick_start/launcher/MainLauncher.java <<'EOF'
package depinject.quick_start.launcher;

import kz.greetgo.depinject.Depinject;

public class MainLauncher {
  public static void main(String[] args) {
    MainBeanContainer mainBeanContainer = Depinject.newInstance(MainBeanContainer.class);

    System.out.println(mainBeanContainer.getHelloWorld().message());
  }
}
EOF

 cat > src/test/java/depinject/quick_start/gen/GenerateAndCompileBeanContainers.java << 'EOF'
package depinject.quick_start.gen;

import kz.greetgo.depinject.gen.DepinjectUtil;

/**
 * Генерирует реализацию BeanContainer-ов
 */
public class GenerateAndCompileBeanContainers {
  public static void main(String[] args) throws Exception {
    String outDir = args.length < 1 ? "build/gen_java_out_dir" : args[0];

    // первым аргументов указывается пакет (и все его подпакеты), в котором будут искаться BeanContainer-ы
    // вторым аргументов указывается директория, где будут сгенерированы и откомпилированны инходники
    DepinjectUtil.implementBeanContainers("depinject.quick_start", outDir);
  }
}
EOF

 # Ready
