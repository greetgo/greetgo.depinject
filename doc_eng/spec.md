### References

 - [Very quick start (using TestNG)](fast_start.md)
 - [Quick start (main function, or war file)](quick_start.md)
 - [Concept](concept.md)
 - [Specification]
   - [Creation of beans](#bean-creation)
     - [Creation of bean using bean class](#creation-of-bean-using-bean-class)
     - [Creation of bean using bean method](#creation-of-bean-using-a-bean-method)
     - [Creation of bean using bean factory](#creation-of-bean-using-a-bean-factory)
   - [Including beans to bean containers](#include-beans-to-bean-containers)
     - [Annotation `@BeanScanner`](#beanscanner-annotation)
     - [Annotation `@ScanPackage`](#scanpackage-annotation)
     - [Annotation `@Include`](#include-annotation)
   - [Bean containers](#bean-containers)
   - [Bean replacers](#replacers)

## Specification

First, it is necessary to read the concept - the basic principles of depinject are described there. It is also possible to use fast or very fast start to get general info about the library.

### Capabilities

  - Initializing beans as needed (rather than as dependency);
  - Code generation instead of reflection, so that optimization works;
  - Three ways to create beans;
  - Two ways to replace beans (the analog of aspect programming);
  
### Bean Creation

A bean is an object that can be accessed from the bean class using the BeanGetter interface.

There are only three ways to create beans:
   - by means of a bean class;
   - by means of a bean method;
   - by means of a bean factory;

#### Creation of bean using bean class

A bean class is a class marked with @Bean annotation.  The bean class must have a default constructor in order the library was able to create the instance of this bean. The system creates an instance of this class using the default constructor.

#### Creation of bean using a bean method

A bean method is a public method of a certain bean, marked with `@Bean` annotation. The object returned by this method automatically become a bean. So, it is possible to create beans without a default constructor.

#### Creation of bean using a bean factory

Interface or abstract class can be marked with `@Bean` annotation. In this case depinject does not know how to create such a bean, and it needs help. This assistance can be provided by a bean factory.

A bean factory is a bean that implements the BeanFactory interface, which contains one method:

```java
public interface BeanFactory {
  Object createBean(Class<?> beanClass) throws Exception;
}
```

This method is used to create beans.The interface or abstract class marked with `@Bean`is transferred to it, and what is returned by this method will become a bean. Bean factory is specified in `@BeanConfig` annotation.

Besides, the bean factory can be specified in the interface or in the abstract class in `@FactoredBy` annotation.

The bean factory specified in `@BeanConfig` is applied to all beans that are related to this bean-config by @Include annotation as well as by `@BeanScanner` annotation. Following `@Include`, internal bean factories can be met,

> internal bean factories are more important than general ones.

Also, the bean factory, defined by `@FatoredBy` annotation, is more important than the bean factory defined by the bean-config.

If the bean factory is not defined, but `@Bean` annotation was met at the interface or abstract class, build error is generated.


For example, it is possible to create bean-interface as follows:

```java
@BeanConfig(defaultFactoryClass = ExampleFactory.class)
public class BeanConfigExample {}

@Bean // the factory also is a  bin
public class ExampleFactory implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) throws Exception {
    if (beanClass == Interface1.class) return () -> "Hello World!";
    if (beanClass == Interface2.class) return () -> 42;
    if (beanClass == InterfaceAsd.class) throw new RuntimeException("This exception will never happened");
            
    throw new RuntimeException("I do not known how to create " + beanClass);
  }
}

@Bean
public class ExampleFactoryAsd implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) throws Exception {
    if (beanClass == InterfaceAsd.class) return () -> "asd";
    
    throw new RuntimeException("I do not known how to create " + beanClass);
  }
}

@Bean
public interface Interface1 {
  String message();
}

@Bean
public interface Interface2 {
  int value();
}

@Bean
@FactoredBy(ExampleFactoryAsd.class)
public interface InterfaceAsd {
  String asd();
}

//Now we can connect interfaces:
@Bean
public class UsingInterfaces {
  public BeanGetter<Integerface1> f1;
  public BeanGetter<Integerface2> f2;
  public BeanGetter<IntegerfaceAsd> asd;
  
  public void printMessages() {
    System.out.println("f1 message is " + f1.get().message());
    System.out.println("f2 value   is " + f2.get().value());
    System.out.println("asd        is " + f2.get().asd());
  }
}

```

### Include Beans to Bean Containers

Connecting the bean to the bean container is done in two stages:
  1. Connecting a bean to a bean-config,
  2. Connecting the bean-config to the bean container directly or through another bean-config.

Connecting a bean to a bean-config is done using [`@BeanScanner` annotation](#beanscanner-annotation).

Connecting a bean-config to a bean container or another bean-config is done using [`@Include` annotation](#include-annotation).

It is also possible to connect beans to a bean configuration using [`@ScanPackage` annotation](#scanpackage-annotation).
It is not recommended to use `@ScanPackage` annotation, because code refactoring becomes more complicated.
The `@ScanPackage` annotation was introduced so that to connect the beans received during code generation.

#### BeanScanner Annotation

`@BeanScanner` annotation serves to connect local beans to a bean-config

> > Local beans are the beans that are in this package and in all of its subpackage
>
> > In other words: a subpackage is another package inside this package.
> 
> Or mathematically speaking:
> 
>  > A subpackage of this package, in terms of the depinject library, is another package whose name begins
>  > with the name of this package.
>
> Well, it turns out that
>
> > Subpackage of subpackage is also a subpackage
>
> For example, we have two packages:

    kz.greetgo.main - package #1
    kz.greetgo.main.register.impl - package #2

> So, package #2 is a subpackage of package #1

`@BeanScanner` annotation, being set to the bean-config (next to `@BeanConfig` annotation), obliges the system
to connect all the beans that are in this package and in all its subpackages to this bean-config.

#### ScanPackage Annotation

This annotation allows to connect not local beans, i.e. beans that are in another package, or in other
packages. This annotation is indicated in the bean-config (next to `@BeanConfig` annotation). Also in this annotation,
it is possible to specify paths to one or more packages. Paths can be relative and absolute.

The absolute path to the package is the name of the package.

The relative path begins from the point or from the lid (^).

A path starting with a point indicates the package, whose name is obtained from the concatenation of the current
package and path.

For example, if you place a bean-config with the annotation in `kz.greetgo.main` package:

    @ScanPackage(".register.impl")

then beans from `kz.greetgo.main.register.impl` package and from all its subpackages will be connected to
this bean-config.

Besides, the path can start with one or more lids (^). One lid means a parent package, two - grandfather's package,
and so on. You can put a point after the lid as well as not put.

For example, if you place a bean-config with the annotation in `kz.greetgo.main.report.impl` package:

    @ScanPackage("^^.register.impl")

or

    @ScanPackage("^^register.impl")

then beans from `kz.greetgo.main.register.impl` package and from all its subpackages will be connected to this
bean-config.

The use of `@ScanPackage` annotation is highly not recommended because it makes code refraction more difficult.
This annotation may be deleted In the following versions of the library,.

#### Include Annotation

Beans are connected to the bean-config, the bean-config is connected to the bean-factory with the help of `@Include`
annotation,  which specifies the connectible bean-config (one or more). Also, other bean-configs can be connected
to the bean-config.

It is possible to create a bean-config with no beans. It will be useful because other bean-configs are connected to it.
It is a kind of bean-config aggregator, a kind of subsystem within the common system. And, when connecting this
bean-config aggregator,the whole subsystem is connected.

For example, there are bean-configs with beans:

```java
package kz.greetgo.hello;
@BeanConfig
@BeanScanner
public class BeanConfigHello {}
```
```java
package kz.greetgo.hello;
@Bean
public class Hello {}
```
```java
package kz.greetgo.world;
@BeanConfig
@BeanScanner
public class BeanConfigWorld {}
```
```java
package kz.greetgo.world;
@Bean
public class Hello {}
```
And the bean-config aggregator:
```java
package kz.greetgo.another;
@BeanConfig
@Include({
  kz.greetgo.hello.BeanConfigHello.class,
  kz.greetgo.world.BeanConfigWorld.class,
})
public class BeanConfigHelloWorld {}
```
`BeanConfigHelloWorld` does not contain its beans, but it contains beans from `BeanConfigHello`
and` BeanConfigWorld` by means of  `@Include` annotation. Now, if we connect `BeanConfigHelloWorld` to the bean
container, then both `Hello` and` World` will be available to the bean container as following:

```java
package kz.greetgo.another_another;

@Include(kz.greetgo.another.BeanConfigHelloWorld.class)
public interface HelloWorldContainer implements BeanContainer {
  Hello getHello();
  World getWorld();
}
```

#### Bean Containers

Bean container is an interface that extends the interface of `@BeanContainer`. The bean container must contain one
or more methods. Methods of the bean container should not contain arguments. Otherwise, there will be a build error.

The return type of the bean container method must uniquely identify one and only bean from that set of beans that
are connected to this bean container. Otherwise, there will be a build error.

The instance of the bean container is created using `Depinject.newInstance` method. At the input of this method,
a bean container class is transferred, and at the output instance of this class is transferred.

A class implementing a bean container must be created by a special generator, compiled and included in the class path.
To generate a bean container implementation, `greetgo.depinject.gen` library and `DepinjectUtil` class are used.

#### Replacers

The library provides the possibility of replacing the beans with the help of the bean replacers (bean changers).
For this purpose, there is a special interface:

```java
public interface BeanReplacer {
  Object replaceBean(Object originalBean, Class<?> returnClass);
}
```

With one method: `replaceBean`. This method is called to replace beans. The first argument to this method is the
original bean, and the second argument is the class from `BeanGetter`. The method must return an object that
is `instanceOf returnClass`. If such an object can not be created or not needed, then it is possible to
return `originalBean`.

To create a bean replacer, the bean is to implement this interface. A bean container may have several bean replacers.

There can also be several bean replacers for one bean. In this case, it applies them in order of priority
indicated by `@ReplacePriority` annotation in bean replacer. If this annotation is not present, it is considered,
that priority value of bean replacer is zero. For the same priority values, bean replacers are arranged in
alphabetical order by the name of the class of the bean replacer.

Bean replacers are not used for all beans, but only for those indicated by annotations:

  - `@ReplaceInstanceOf` - this annotation specifies an interface or class that should extend or implement
                           a bean, so that a bean replacer can be applied to it;
  - `@ReplaceWithAnn` - this annotation specifies another annotation, bean must be marked with, so that
                         this bean replacer can be applied to it.

Bean replacer is marked with these annotations.
