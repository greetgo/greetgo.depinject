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
depinject creates instances of beans only as necessary, then you can put hundreds of thousands beans in a bean-container and
the system will run no slower than several beans.

In depinject, even loading a bean class occurs as needed.

### Beans

> Beans are objects that are automatically created and initiated by the links they need to other beans.

> Bean classes are classes, the instances of which can be beans. I.e. bean is the instance of the bean class.

> Instances of bean classes are created in the bean-container as needed.

In order to turn a class into bean class, it should be marked with `@Bean` annotation. Also, the bean class must have a public
default constructor, otherwise the system will not be able to create instances of this class. There are [there are only three ways to create beans](#bean-creation-variants) (- see below).

The `@Bean` annotation is not enough to create a bean. The bean class must also be connected to the bean-container.

> Connecting bean-classes to bean-containers is done using bean configs

### Bean configs

A bean config is a class without methods and fields inheriting and extending nothing. Bean config should be marked with
`@BeanConfig` annotation - this marking means that it is bean config.

Бин-конфиг может содержать аннотации: `@BeanScanner`, `@Include` (одну из них или обе). 

Аннотация `@BeanScanner` обозначает, что данный бин-конфиг подключает все бин-классы, которые находятся в пакете данного
бин-конфига, а также во всей иерархии подпакетов этого пакета. (Этот конфиг как бы сканирует свой пакет и все
внутренние пакеты на наличие бинов).

Аннотация `@Include` подключает к данному бин-конфигу другие бин-конфиги, которые указанны в этой аннотации.

С помощью аннотаций `@BeanConfig`, `@BeanScanner` и `@Include` можно создавать разветвлённую сеть бинов, внутри
проекта. Можно создать несколько бин-контэйнеров, и каждый бин-контэйнер содержит свой набор бинов. Какие-то бины
подключены к одному бин-контэйнеру, а какие-то к другом, а какие-то бины могут быть подключены
к нескольким бин-контэйнерам.

Таким простым способом можно настроить очень гибкую систему распределения бинов в проекте.
Какие-то бины могут быть только в продукте, какие-то только среди тестов, какие-то и там и там (например те, которых
надо тестировать).

### Точечное соединения бинов

Подключение одного бина к другому происходит с помощью интерфейса `BeanGetter`, например:

```
  public BeanGetter<SomeClass> someBean;
```

Сюда подключается бин, который должен быть `instanceOf SomeClass`. Такой бин должен найтись ровно один. Если их
найдётся больше или вообще ни одного не найдётся, то происходит ошибка сборки.

> Разработчик специально сделал так, чтобы в этом случае происходила ошибка. Чтобы небыло проблем с возвращением
  `null`; или проблем с непонятностью: а какой бин сюда подключиться. А с ошибкой всё понятно: конфигурируй так,
  чтобы был только один бин. И это на практике очень удобно.

### Множественное соединения бинов

Можно к одному бину за раз подключить сразу несколько бинов. Делается это с использованием `java.util.List`, например:

```
  public BeanGetter<List<SomeClass>> beans;
```

Сюда подключаются все бины, которые `instanceOf SomeClass`. Если таких бинов нет, то присвоится пустой массив.
Последовательность, в которой будут лежать бины внутри списка, **НЕ** определена, и возможности её задать нет.

### Синглтоны

Бин-классы могут быть синглтонами, а могут и не быть синглтонами. Если бин не
должен быть синглтоном, то его надо пометить так: `@Bean(singleton = false)`.

По умолчанию бин-класс - синглтон.

Синглтоны создаются и инициируются потокобезопасно.

Следует понимать, что синглтон в depinject-е содержит одну инстанцию в рамках инстанции бин-контэйнера. Если создать
ещё одну инстанцию бин-контэйнера, то в ней будут создаваться новые инстанции бинов-синглтонов. Поэтому depinject-овский
синглтон, не совсем синглтон в классическом понимании. Если бин-контэйнер сделать классическим синглтоном, то все
бины-синглтоны этого контэйнера станут классическими.

### Бин-контэйнер

Бин-контэйнер представляет из себя интерфейс, который должен расширять интерфейс `BeanContainer`. В интерфейсе
`BeanContainer` нет ни каких методов - он служит лишь как индикатор. Самже интерфейс бин-контэйнера должен содержать
методы без параметров. Имена методов значения не имеют. Имеют значения тип возвращаемого значения.

> Тип возвращаемого значения бин-контэйнера должен однозначно определать какой-то один конкретный бин.

Если типу возвращаемого значения соответствует больше одного бина, или не соответствует вообще ни одного, то
происходит ошибка сборки.

Бины к бин-контэйнеру необходимо подключать с помощью аннотации `@Include`.

Бин-контэйнер реализуется автоматически с помощью кодогенерации. Есть метод `DepinjectUtil.implementBeanContainers`
из библиотеки `greetgo.depinject.gen`, который сканирует указанный пакет на наличие интерфейсов бин-контэйнера
и создаёт для каждого найденного интерфейса его реализацию. Потом, эту реализацию, можно инстанциировать
с помощью метода `Depinject.newInstance`.

### Bean creation variants

Существует три способа создания бина:

  - Посредством бин-класса (стандартный вариант);
  - Посредством бин-метода;
  - Посредством бин-фабрики.

#### Создание бина посредством бин-класса

Бин-класс - это класс помеченный аннотацией `@Bean`. У бин-класса должен быть конструктор по умолчанию, чтобы
библиотека смогла создать инстанцию этого бина.

#### Создание бина посредством бин-метода

Бин-метод - это публичный метод некого бина, помеченный аннотацией `@Bean`. Объект, возвращаемый этим методом
автоматически становиться бином. Так можно создавать бины без контруктора по умолчанию.

#### Создание бина посредством бин-фабрики

Аннотацией `@Bean` можно пометить интерфейс или абстрактный класс. В этом случае depinject не знает как создавать
такой бин, и ему нужна помощь. Эту помощь ему может предоставить бин-фабрика

Бин-фабрика - это бин, который реализует интерфейс `BeanFactory`, который в себе содержит один метод:

```java
public interface BeanFactory {
  Object createBean(Class<?> beanClass) throws Exception;
}
```

Этот метод служит для создания бинов. Ему передаётся интерфейс или абстрактный класс, помеченный `@Bean`-ом, и то, что
этот метод вернёт становиться бином. Бин-фабрика указывается в аннотации `@BeanConfig`.

Также бин-фабрику можно указать в самом интерфейсе или абстрактном классе в аннотации `@FactoredBy`.

Бин-фабрика, указанная в `@BeanConfig-е` распространяется на все бины, которые относятся к этому бин-конфигу, как по
пути аннотации `@Include` так и по пути аннотации `@BeanScanner`. Следуя по `@Include` могут встретиться внутренние
бин-фабрики,

> внутренние бин-фабрики приоритетнее более общих.

Также, бин-фабрика, определённая аннотацией `@FatoredBy`, более приоритетна чем бин-фабрика, определённая бин-конфигом.

Если бин-фабрика не определена, но аннотация `@Bean` встретилась у интерфейса или абстрактного класса, то генерируется
ошибка сборки.
