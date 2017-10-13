### Ссылки

 - [Очень быстрый старт (через TestNG)](fast_start.md)
 - [Быстрый старт (main-функция или war-файл)](quick_start.md)
 - [Концепция](concept.md)
 - [Спецификация]

## Спецификация

### Возможности

  - Инициализация бинов по мере необходимости (а не по мере зависимости);
  - Кодогенерация вместо рефлексии, чтобы работала оптимизация;
  - Три способа создания бинов;
  - Два способа подмены бинов (некий аналог аспектного программирования);
  
### Создание бинов

Бин - это объект, к которому можно получить доступ из бин-класса, посредством интерфейса `BeanGetter`.

Существует только три способа создания бинов:
  - посредством бин-класса;
  - посредством бин-метода;
  - посредством бин-фабрики;

#### Создание бина посредством бин-класса

Бин-класс - это класс помеченный аннотацией `@Bean`. У бин-класса должен быть конструктор по умолчанию, чтобы
библиотека смогла создать инстанцию этого бина. Система создаёт инстанцию этого класса, используя конструктор
по умолчанию.

#### Создание бина посредством бин-метода

Бин-метод - это публичный метод некого бина, помеченный аннотацией `@Bean`. Объект, возвращаемый этим методом
автоматически становиться бином. Так можно создавать бины без контруктора по умолчанию.

#### Создание бина посредством бин-фабрики

Аннотацией `@Bean` можно пометить интерфейс или абстрактный класс. В этом случае система не знает как создавать
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

Также, бин-фабрика, определённая аннотацией `@FatoredBy`, более приоритетна чем бин-фабрика,
определённая бин-конфигом.

Если бин-фабрика не определена, но аннотация `@Bean` встретилась у интерфейса или абстрактного класса, то генерируется
ошибка сборки.

Например можно создавать бины интерфейсы следующим образом:

```java
@BeanConfig(defaultFactoryClass = ExampleFactory.class)
public class BeanConfigExample {}

@Bean // фабрика тоже бин
public class ExampleFactory implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) throws Exception {
    if (beanClass == Interface1.class) return () -> "Hello World!";
    if (beanClass == Interface2.class) return () -> 42;
    if (beanClass == InterfaceAsd.class) throw new RuntimeException("Эта ошибка никогда не вылетит");
            
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

//Теперь сможем подключать интерфейсы:
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