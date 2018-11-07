
### Change list

#### Changes: from 2.0.0 ⟶ to 2.0.1

 - Made check: BeanGetter must be public;
   > If BeanGetter is non-public, then BeanGetterIsNotPublic error will be generated.
   >
   > This is done because programmers constantly forget that BeanGetter must be public, and then they catch
   > the error NullPointerException and do not understand for a long time what the matter is.
   >
   > Now an error is immediately generated, and it becomes immediately clear to the programmer what is wrong.

 - Added annotation @LetBeNonePublic in case the programmer STILL wants to add a non-public BeanGetter

#### Changes: from 2.1.5 ⟶ to 2.1.6
 - Renamed annotation @LetBeNonePublic ⟶ to @SkipInject
   > Programmer puts annotation @LetBeNonePublic and thinks that depinject initializes it, bit it is not so.
   > Annotation @SkipInject has another meaning.
 
 - Added constructors with BeanGetter arguments.
   > Now you can add BeanGetter to constructor arguments, for example:
   > ```java
   > @Bean
   > public class SomeBean {
   >   // this line do not generate BeanGetterIsNotPublic error because constructor contains such type
   >   private final BeanGetter<SomeAnotherBean> a;
   >
   >   public SomeBean(BeanGetter<SomeAnotherBean> a) {
   >     this.a = a;
   >   }
   > }
   > ```

 - Renamed BeanGetterIsNotPublic ⟶ to NonPublicBeanWithoutConstructor
   > Because added constructors with BeanGetter arguments, and now non-public BeanGetter is OK, if it is
   > initialized through constructor.

#### Changes: from 2.1.6 ⟶ to 2.1.7
  - Added id in annotation @Bean
  - Added annotation @Qualifier to select beans by id
