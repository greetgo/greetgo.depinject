# greetgo.depinject

[Russian language here](README.md)

Implementation of Dependency Injection pattern based on code generation by greetgo!.

### References

 - [Change list](doc_eng/change_list.md)

 - [Very quick start (using TestNG)](doc_eng/fast_start.md)
 - [Quick start (main function, or war file)](doc_eng/quick_start.md)
 - [Concept](doc_eng/concept.md)
 - [Specification](doc_eng/spec.md)
 

# Features

 - NO dependencies;
 - Minimum size;
   > What goes into production, contains only one class with one small static method, the rest is
     interfaces and annotations
 - The definition of the beans topology (the speed of this operation is O (n*n), where n - the number of beans), occurs at the compilation
   stage, not at startup;
   > This allows to make the system startup always fast, and independent of the number of beans in the system
 - Initialization of beans occurs in the order of use, not in the order of dependencies, therefore,
   if the beans are not used, then they are not created
   > For example, BEAN_1 contains a reference to BIN_2. Now, when initializing BIN_1, BIN_2 will not be initiated. BIN_2
     will be initiated only when it is called directly. This makes the speed of the system startup
     independent of the number of beans.
 - Reflection is not used in bean-containers, hence everything is properly optimized!

# Disadvantages

  - a call to the bean can only be done through the method `get()`. Unfortunately it does not work out directly (as in Spring  
  @Autowired), because the bean must be initialized at the first call, not when the dependency is formed.
    
    Call can be done in this way:
  
```java
  class SomeBean {
    public BeanGetter<SomeAnotherBean> someAnotherBean;
    public void someMethod() {
        someAnotherBean.get().helloWorld();
    }
  }
```
  - Fields `BeanGetter` must be `public`! (Because the reflection is not used in production);
  - Complicated build (it is necessary to generate the bean-container code, compile and add it to the distribution);
  - Currently, only TestNG is supported. (It's great if someone запилит greetgo.depinject.junit) ;
