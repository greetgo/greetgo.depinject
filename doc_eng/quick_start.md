### References

 - [Very quick start (using TestNG)](fast_start.md)
 - [Quick start (main function, or war file)]
 - [Concept](concept.md)
 - [Specification](spec.md)

## Quick start (main function или war file)

### Prerequisites:

  - java 1.8+
  - gradle 3.5+ (https://gradle.org/)

### Introduction

Bean-containers are interfaces that extend the interface of BeanContainer, and it seems to contain all the beans.
In order to refer to a specific bean from a bean-container, it is necessary to define the method in the bean container interface without parameters (for bean container, all methods are without parameters). The return type of this method must uniquely determine one bean.

In our example project, the input point is the main function.

In the main function, it is necessary to create a bean container instance and use it to start some process inside the beans. Then, in order to make jar-file work, it is necessary, to generate a bean container implementation at the moment of building, and add it to the distribution. In the example project, this distribution will be a jar-file with the main class, which contains all the dependencies.

### Preparation of the example project

Let's create a gradle project. To create a project, you can:

  - either create a project through bash in one movement - [here](quick_start.script.sh),
    and then jump into [here](#run-hello-world), to skip manual creation;

  - or manually, as described below:

Here is the structure of the project:

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

Contents of file `build.gradle`:

```groovy
apply plugin: 'java'
apply plugin: 'maven'
apply plugin: 'idea'

repositories {
  jcenter()
}

dependencies {
  ext.depinjectVersion = '2.0.0'

  //This is very small and does not contain any dependencies
  compile "kz.greetgo.depinject:greetgo.depinject:$depinjectVersion"

  //This is needed to generate BeanConfig implementation -  it does not go to production
  testCompile "kz.greetgo.depinject:greetgo.depinject.gen:$depinjectVersion"
}

task generateBeanContainers(
  type: JavaExec,
  description: 'This task generates a BeanContainers' implementation and compiles them'
) {
  ext.genDir = "${project.buildDir}/generated/bean_container_impl"

  main = 'depinject.quick_start.gen.GenerateAndCompileBeanContainers'
  args = ["${genDir}"]

  classpath { [sourceSets.test.runtimeClasspath,] }
}

task beanContainerJar(
  type: Jar,
  description: 'This task generates a jar file with the BeanContainers implementation sources'
) {
  dependsOn generateBeanContainers
  baseName "bean-containers"
  from generateBeanContainers.genDir
}

//The main JAR file is generated here
jar {
  dependsOn beanContainerJar

  manifest {
    attributes "Main-Class": "depinject.quick_start.launcher.MainLauncher"
  }

  //Collect all the dependencies in one file
  from {
    configurations.compile.collect { it.isDirectory() ? it : zipTree(it) }
  }

  //Add the implementation of BeanContainers to the main JAR
  with beanContainerJar
}
```

Contents of file `BeanConfigHelloWorld.java`:

```java
package depinject.quick_start.beans;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;

@BeanConfig
@BeanScanner
public class BeanConfigHelloWorld {}

```

Contents of file `Hello.java`:

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

Contents of file `World.java`:

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

Contents of file `HelloWorld.java`:

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

Contents of file `MainBeanContainer.java`:

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

Contents of file `MainLauncher.java`:

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

##### Run Hello World

Now, to start it all, go to `depinject.quick_start/` folder and run the command:

    gradle jar

At the end you will see:

    BUILD SUCCESSFUL

We got the compiled jar file. Now we run it with the command:

    java -jar build/libs/depinject.quick_start.jar

Here is a message:

    Hello World!!!

### Description of the example project

The `depinject.quick_start.beans` contains a set of beans that are somehow connected together.

`MainBeanContainer.java` file specifies the BeanContainer, which contains all the beans. These beans are connected to this
container using the annotation `@Include`.

`MainLauncher.java` file contains the main function that initiates BeanContainer and pulls HelloWorld bean out and works with it.

`build.gradle` specifies the build option. `generateBeanContainers` task performs an analysis of all the links of all beans for all bean containers of the specified package and the whole hierarchy of its subpackets, and after the analysis generates the source code of bean containers. This source code is placed 
`depinject.quick_start/build/generated/bean_container_impl/depinject/quick_start/launcher/` folder. The implementation of bean container can be found there as well:

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
