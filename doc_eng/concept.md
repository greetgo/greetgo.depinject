### References

 - [Very quick start (using TestNG)](fast_start.md)
 - [Quick start (main function, or war file)](quick_start.md)
 - [Concept]
 - [Specification](spec.md)

### Concept

If there are a lot of objects in the program and the relations between them are very complex, then the code for initialization can be very complicated. And even more difficult to maintain it in an adequate state. Besides, everything can be complicated by the fact that it is necessary to have several options for initializing the objects.

It would be great if all this work could be discharged from the duty of the programmer and delegated to the computer - so that it would do it itself, and the programmer would only deal with a specific task. The programmer would just need to say what he wanted and "some magical thing" gave it to him, moreover, immediately.

Dependency Injection pattern was invented to solve this problem.
And depinject solves this problem.

Depinject library works with special objects called beans. Beans describe what is needed and the library provides.

> Well, beans always need other beans, and nothing more

It's easy enough to implement, and depinject can. The main algorithm of depinject looks like this:

> We create beans using the annotation `@Bean`. But only connected beans are used. We connect beans with the help of 
  `@BeanConfig`, not forgetting to use` @BeanScanner`. Next, we create `BeanContainer`, and, using
  annotations `@Include`, connect created` @BeanConfig` to it. Then, we take the necessary beans  (which are already correctly   
  initiated) out of BeanContainer and use them.
  Bins refer to each other via `BeanGetter`.

Using depinject in combination with inheritance, you can very flexibly configure the internal infrastructure of the project. As
depinject creates instances of beans only as necessary, then you can put hundreds of thousands beans in a bean container and
the system will run no slower than several beans.

In depinject, even loading a bean class occurs as needed.

### Beans

> Beans are objects that are automatically created and initiated by the links they need to other beans.

> Bean classes are classes, the instances of which can be beans. I.e. bean is the instance of the bean class.

> Instances of bean classes are created in the bean container as needed.

In order to turn a class into bean class, it should be marked with `@Bean` annotation. Also, the bean class must have a public
default constructor, otherwise the system will not be able to create instances of this class. There are [there are only three ways to create beans](#bean-creation-variants) (- see below).

The `@Bean` annotation is not enough to create a bean. The bean class must also be connected to the bean container.

> Connecting bean classes to bean containers is done using bean configs

### Bean configs

A bean config is a class without methods and fields inheriting and extending nothing. Bean config should be marked with
`@BeanConfig` annotation - this marking means that it is bean config.

A bean config can contain the following annotations: `@BeanScanner`,` @Include` (one of them or both).

`@BeanScanner` annotation specifies that this bean config connects all the bea classes that are in the package of this bean config, as well as in the whole hierarchy of subpackages of this package. (This config seems to scan its package and all internal packages for the presence of beans).

`@Include` annotation connects other bean configs, which are indicated in this annotation, to this bean config.

With the help of `@BeanConfig`,`@BeanScanner` and `@Include` annotations, it is possible to create a branched network of beans within the project. It is possible to create several bean-containers, and each bean container contains its own set of beans. Some beans are connected to one bean container, some to another, some beans can be connected to several bean containers.

In this simple way, it is possible to configure a very flexible system for distributing beans in a project.
Some beans can only be in the product, some only among the tests, some can be both in the product and tests (for example, those that are to be tested).

### Point-to-Point Bean Connections

The connection of one bean to another is done using `BeanGetter` interface, for example:

```
  public BeanGetter<SomeClass> someBean;
```

This connects the bean, which must be `instanceOfSomeClass`. There should be exactly one bean. If there are more or none at all, then an build error occurs.

> The developer purposely made in this way to cause an error. To avoid any problems with the return `null`; or problems with    
  incomprehensibility: which bean to connect here. When here is an error, everything is clear: configure it in such a way,
   so that there is only one bean. And this is very convenient in practice.

### Multiple Bean Connections

It is possible to connect several beans to a single bean at a time. This is done using `java.util.List`, for example:

```
  public BeanGetter<List<SomeClass>> beans;
```

All the beans that are `instanceOf SomeClass` are connected here. If there are no such beans, an empty array will be assigned.
The sequence in which the beans within the list will be set is **NOT** defined, and there is no possibility to define it.

### Singletons

Bean classes can be singletons, or they may not be singletons. If bean should not be a singleton, then it should be marked like this: `@Bean(singleton = false)`.

By default, the bean class is a singleton.

Singletons are created and initiated thread-safe.

It should be understood that the depinject singleton contains one instance within the bean container instance. If you create one more instance of bean container, then new instances of bean-singletons will be created in it. Therefore depinject
singleton is not exactly a singleton in the classical sense. If the bean container is made as a classical singleton, then all
the beans-singletons of this container will become classical.

### Bean Container

The bean container is an interface that should extend the interface of `BeanContainer`. `BeanContainer` interface does not have any methods - it serves only as an indicator. The interface of the bean container must contain methods without parameters. Names of methods do not matter. The return type is important.

> The return type of a bean container must uniquely identify one particular bean.

If the type of the return value corresponds to more than one bean, or corresponds to no one, then a build error occurs.

Beans must be connected to the bean container using `@ Include` annotation.

The bean container is implemented automatically by code generation. There is `DepinjectUtil.implementBeanContainers` method
from `greetgo.depinject.gen` library, which scans the specified package for the presence of bean container interfaces
and creates its implementation for each found interface . Then, this implementation can be instantiated using  `Depinject.newInstance` method.

### Bean creation variants

There are three ways to create a bean:

  - By means of a bean class (standard);
  - By means of a bean method;
  - By means of a bean factory.

#### Creating a bean using a bean class

A bean class is a class marked with `@Bean` annotation. The bean class must have a default constructor in order
the library was able to create the instance of this bean.

#### Creating a bean using a bean method

A bean method is a public method of a certain bean, marked with `@Bean` annotation. The object returned by this method
automatically become a bean. So, it is possible to create beans without a default constructor.

#### Creating a bean with a bean factory

Interface or abstract class can be marked with `@Bean` annotation. In this case depinject does not know how to create
such a bean, and it needs help. This assistance can be provided to it by a bean factory.

A bean factory is a bean that implements the BeanFactory interface, which contains one method:

```java
public interface BeanFactory {
  Object createBean(Class<?> beanClass) throws Exception;
}
```

This method is used to create beans.The interface or abstract class marked with `@Bean` is transferred to it, and what is
returned by this method will become a bean. Bean factory is specified in `@BeanConfig` annotation.

Besides, the bean factory can be specified in the interface or in the abstract class in `@FactoredBy` annotation.

The bean factory specified in `@BeanConfig` is applied to all beans that are related to this bean-config by `@Include` annotation as well as by` @BeanScanner` annotation. Following `@Include`, internal bean factories can be met,

> internal bean factories are more important than general ones.

Also, the bean factory, defined by `@FatoredBy` annotation, is more important than the bean factory defined by the bean-config.

If the bean factory is not defined, but `@Bean` annotation was met at the interface or abstract class, build error is generated.
