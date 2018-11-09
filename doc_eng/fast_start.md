### References

 - [Very quick start (using TestNG)]
 - [Quick start (main function, or war file)](quick_start.md)
 - [Concept](concept.md)
 - [Specification](spec.md)

## Very quick start (using TestNG)

### Prerequisites:

  - java 1.8+
  - gradle 3.5+ (https://gradle.org/)

### Introduction

It is very easy to use depinject on TestNG.
For this, the class-test must be inherited from a special abstract class and contain one annotation.

Nothing more is needed.

However, in this case, binding of beans will occur at runtime, but for tests this is not critical.

(Theoretically, it should also be very simple to use it on JUnit, but it still needs to be implemented:) ) 

### Example project preparation

Let's create a gradle project. To create a project, you can:

  - either create a project by one movement through bash,  - [here](fast_start.script.sh),
    and then jump into [here](#run-tests), in order to skip the manual creation;

  - or manually, as described below:

Project file structure:

    depinject.fast_start/
      build.gradle
      src/main/java/depinject/fast_start/
        BeanConfigHelloWorld.java
        Hello.java
        World.java
        HelloWorld.java
      src/test/java/depinject/fast_start/
        HelloWorldTest.java


Content of file `build.gradle`:

```groovy
apply plugin: 'java'
apply plugin: 'maven'
apply plugin: 'idea'

repositories {
  jcenter() //Baruch, hi
}

dependencies {
  ext.depinjectVersion = '2.1.7'
  ext.testNgVersion = '6.5.1'

  compile "kz.greetgo.depinject:greetgo.depinject:$depinjectVersion"

  testCompile "kz.greetgo.depinject:greetgo.depinject.testng:$depinjectVersion"

  testCompile "org.testng:testng:$testNgVersion"
  testCompile 'org.easytesting:fest-assert-core:2.0M10'
}

test { useTestNG() }
```

Content of file `BeanConfigHelloWorld.java`:

```java
package depinject.fast_start;

import kz.greetgo.depinject.core.*;

@BeanConfig
@BeanScanner
public class BeanConfigHelloWorld {}

```

Content of file `Hello.java`:

```java
package depinject.fast_start;

import kz.greetgo.depinject.core.*;

@Bean
public class Hello {

  public String message() {
    return "Hello";
  }

}

```

Content of file `World.java`:

```java
package depinject.fast_start;

import kz.greetgo.depinject.core.*;

@Bean
public class World {

  public String message() {
    return "World";
  }

}
```

Content of file `HelloWorld.java`:

```java
package depinject.fast_start;

import kz.greetgo.depinject.core.*;

@Bean
public class HelloWorld {

  public BeanGetter<Hello> hello;
  public BeanGetter<World> world;

  public String message() {
    return hello.get().message() + " " + world.get().message() + "!!!";
  }

}
```

Content of file `HelloWorldTest.java`:

```java
package depinject.fast_start;

import kz.greetgo.depinject.core.*;
import kz.greetgo.depinject.testng.*;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfigHelloWorld.class)
public class HelloWorldTest extends AbstractDepinjectTestNg {

  public BeanGetter<HelloWorld> helloWorld;

  @Test
  public void helloWorldTest() {
    assertThat(helloWorld).isNotNull();
    String message = helloWorld.get().message();
    assertThat(message).isEqualTo("Hello World!!!");
    
    System.err.println();
    System.err.println();
    System.err.println(message);
    System.err.println();
    System.err.println();
  }

}
```

##### Run Tests

Now, to start it all, go to `depinject.fast_start/` folder and run the command:

    gradle test

At the end you will see:

    BUILD SUCCESSFUL

This message signals that all tests have started and were successful.
All the links were well connected, and everything worked out as it should.

### Example project description

The file `HelloWorldTest.java` contains a test that connects `HelloWorld` bean using the depinject library
and uses it.  In addition, if we go into `HelloWorld.java` file, we will see that it uses `Hello` 
and `World` classes, and it also gets access to them using depinject.

As you can see everything works.

So, you can test everything. But if we need a war file or a main function, we need to create a BeanContainer
and initiate it. And then it will also be necessary to compile and build all the things correctly.
How to do this is described in [quick start](quick_start.md).
