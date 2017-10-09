### Ссылки

 - [Очень быстрый старт (через TestNG)](greetgo.depinject.parent/doc/fast_start.md)
 - [Быстрый старт (main-функция или war-файл)](greetgo.depinject.parent/doc/quick_start.md)
 - [Концепция]
 - [Спецификация](greetgo.depinject.parent/doc/spec.md)

### Концепция

Создаём бины с помощью аннотации `@Bean`. Но бины используются только те, которые подключены. Подключаем бины
с помощью `@BeanConfig`, не забывая использовать `@BeanScanner`. Далее создаём `BeanContainer`, и, с помощью
аннотации `@Include`, подключаем к нему созданные нами `@BeanConfig`-и. Далее из BeanContainer-а вытаскиваем
нужные нам бины и используем их.

### Быстрый старт

