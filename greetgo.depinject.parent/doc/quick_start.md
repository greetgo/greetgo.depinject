### Ссылки

 - [Очень быстрый старт (через TestNG)](greetgo.depinject.parent/doc/fast_start.md)
 - [Быстрый старт (main-функция или war-файл)]
 - [Концепция](greetgo.depinject.parent/doc/concept.md)
 - [Спецификация](greetgo.depinject.parent/doc/spec.md)

## Быстрый старт (main-функция или war-файл)

### Что должно быть предуставнолено:

  - java 1.8+
  - gradle 3.5+ (https://gradle.org/)

### Введение

В функции main необходимо создать инстанцию BeanContainer-а и использовать её для запуска какого-нибудь
процесса внутри бинов. И далее, чтобы работал jar-файл, необходимо при сборке сгенерировать треализацию бин-контэйнера
и добавить её в дистрибутив. К проекте-примере таким дистрибутивом будет jar-файл c main-классом, который в
себе содержит все зависимости.

### Подготовка проекта-примера

Давайте создадим gradle-проект. Чтобы создать проект, можно:

  - либо одним движением через bash создать проект - [здесь](quick_start.script.sh),
    и далее прыгаем [сюда](#run-tests), чтобы пропустить создание вручную;

  - Либо вручную далее по тексту:

Вот структура проекта:

    depinject.quick_start/
      build.gradle
      src/main/java/depinject/quick_start/
        beans/
          BeanConfigHelloWorld.java
          Hello.java
          World.java
          HelloWorld.java
        launcher/
          MainLauncher.java
          MainBeanContainer.java
      src/test/java/depinject/quick_start/gen/
        GenerateAndCompileBeanContainers.java

Содержимое файла `build.gradle`:

```groovy
apply plugin: 'java'
apply plugin: 'maven'
apply plugin: 'idea'

repositories {
  jcenter() //Baruch, hi
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
//  dependsOn autoimplJar

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
```

Содержимое файла `BeanConfigHelloWorld.java`:

```java
package depinject.quick_start.beans;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;

@BeanConfig
@BeanScanner
public class BeanConfigHelloWorld {}

```

Содержимое файла `Hello.java`:

```java
package depinject.quick_start.beans;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Hello {

  public String message() {
    return "Hello";
  }

}

```

Содержимое файла `World.java`:

```java
package depinject.quick_start.beans;

import kz.greetgo.depinject.core.Bean;

@Bean
public class World {

  public String message() {
    return "World";
  }

}
```

Содержимое файла `HelloWorld.java`:

```java
package depinject.quick_start.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class HelloWorld {

  public BeanGetter<Hello> hello;
  public BeanGetter<World> world;

  public String message() {
    return hello.get().message() + " " + world.get().message() + "!!!";
  }

}
```

Содержимое файла `MainBeanContainer.java`:

```java
package depinject.quick_start.launcher;

import depinject.quick_start.beans.BeanConfigHelloWorld;
import depinject.quick_start.beans.HelloWorld;
import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;

@Include(BeanConfigHelloWorld.class)
public interface MainBeanContainer extends BeanContainer {
  HelloWorld getHelloWorld();
}
```

Содержимое файла `MainLauncher.java`:

```java
package depinject.quick_start.launcher;

import depinject.quick_start.beans.HelloWorld;
import kz.greetgo.depinject.Depinject;

public class MainLauncher {
  public static void main(String[] args) {
    MainBeanContainer mainBeanContainer = Depinject.newInstance(MainBeanContainer.class);

    HelloWorld helloWorld = mainBeanContainer.getHelloWorld();
    
    System.out.println(helloWorld.message());
  }
}
```

##### Run Tests

Теперь чтобы это всё запустить, заходим в папку `depinject.quick_start/` и запскаем комаду:

    gradle jar

В конце должно вылететь:

    BUILD SUCCESSFUL

Получили откомпилированный jar-файл, теперь запускаем его командой:

    java -jar build/libs/depinject.quick_start.jar

Вылетает сообщение:

    Hello World!!!

### Описание проекта-примера

В пакете `depinject.quick_start.beans` находиться некий набор бинов, которые между собой как-нибудь перевязаны.

В файле `MainBeanContainer.java` указан BeanContainer, в котором содержаться все бины. Эти бины подключены к этому
контэйнеру с помощью аннотации `@Include`.

В файле `MainLauncher.java` содержится main-функция, которая инициирует наш BeanContainer и вытаскивает из
него бин HelloWorld и работает с ним.

В `build.gradle` указан вариант сборки. Таска `generateBeanContainers` производит анализ всех связей всех бинов для
всех бин-контэйнеров указанного пакета и всей иерархии его подпакетов, и после анализа генерирует исходный код
бин-контэйнеров. Этот исходный код помещается в папку
`depinject.quick_start/build/generated/bean_container_impl/depinject/quick_start/launcher/`. Там можно найти реализацию
бин-контэйнера:

```java
package depinject.quick_start.launcher;
public final class MainBeanContainerAutomaticallyGeneratedImplementation implements depinject.quick_start.launcher.MainBeanContainer{

  private final java.lang.Object forSynchronizedBlocks = new java.lang.Object();

  //
  // Bean container methods
  //

  @java.lang.Override
  public depinject.quick_start.beans.HelloWorld getHelloWorld() {
    return getter_native_HelloWorld_2.get();
  }

  //
  // Bean creations
  //

  private final java.util.concurrent.atomic.AtomicReference<depinject.quick_start.beans.Hello> cachedValue_native_Hello_1 = new java.util.concurrent.atomic.AtomicReference<>(null);
  private final kz.greetgo.depinject.core.BeanGetter<depinject.quick_start.beans.Hello> getter_native_Hello_1 = this::get_native_Hello_1;
  private depinject.quick_start.beans.Hello get_native_Hello_1 () {
    {
      depinject.quick_start.beans.Hello x = cachedValue_native_Hello_1.get();
      if (x != null) return x;
    }
    synchronized (forSynchronizedBlocks) {
      {
        depinject.quick_start.beans.Hello x = cachedValue_native_Hello_1.get();
        if (x != null) return x;
      }
      try {
        depinject.quick_start.beans.Hello localValue = new depinject.quick_start.beans.Hello();
        cachedValue_native_Hello_1.set(localValue);
        return localValue;
      } catch (java.lang.Exception e) {
        if (e instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException) e;
        throw new java.lang.RuntimeException(e);
      }
    }
  }

  private final java.util.concurrent.atomic.AtomicReference<depinject.quick_start.beans.HelloWorld> cachedValue_native_HelloWorld_2 = new java.util.concurrent.atomic.AtomicReference<>(null);
  private final kz.greetgo.depinject.core.BeanGetter<depinject.quick_start.beans.HelloWorld> getter_native_HelloWorld_2 = this::get_native_HelloWorld_2;
  private depinject.quick_start.beans.HelloWorld get_native_HelloWorld_2 () {
    {
      depinject.quick_start.beans.HelloWorld x = cachedValue_native_HelloWorld_2.get();
      if (x != null) return x;
    }
    synchronized (forSynchronizedBlocks) {
      {
        depinject.quick_start.beans.HelloWorld x = cachedValue_native_HelloWorld_2.get();
        if (x != null) return x;
      }
      try {
        depinject.quick_start.beans.HelloWorld localValue = new depinject.quick_start.beans.HelloWorld();
        localValue.hello = (kz.greetgo.depinject.core.BeanGetter<depinject.quick_start.beans.Hello>)(java.lang.Object)getter_native_Hello_1;
        localValue.world = (kz.greetgo.depinject.core.BeanGetter<depinject.quick_start.beans.World>)(java.lang.Object)getter_native_World_3;
        cachedValue_native_HelloWorld_2.set(localValue);
        return localValue;
      } catch (java.lang.Exception e) {
        if (e instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException) e;
        throw new java.lang.RuntimeException(e);
      }
    }
  }

  private final java.util.concurrent.atomic.AtomicReference<depinject.quick_start.beans.World> cachedValue_native_World_3 = new java.util.concurrent.atomic.AtomicReference<>(null);
  private final kz.greetgo.depinject.core.BeanGetter<depinject.quick_start.beans.World> getter_native_World_3 = this::get_native_World_3;
  private depinject.quick_start.beans.World get_native_World_3 () {
    {
      depinject.quick_start.beans.World x = cachedValue_native_World_3.get();
      if (x != null) return x;
    }
    synchronized (forSynchronizedBlocks) {
      {
        depinject.quick_start.beans.World x = cachedValue_native_World_3.get();
        if (x != null) return x;
      }
      try {
        depinject.quick_start.beans.World localValue = new depinject.quick_start.beans.World();
        cachedValue_native_World_3.set(localValue);
        return localValue;
      } catch (java.lang.Exception e) {
        if (e instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException) e;
        throw new java.lang.RuntimeException(e);
      }
    }
  }

  //
  // Bean references
  //
  
  //
  // Getter creations
  //
}
```