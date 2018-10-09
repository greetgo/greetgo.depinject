
### Change list

#### Changes 2.0.0 -> 2.0.1

 - A check was made to be sure that `BeanGetter` was public;
   > If `BeanGetter` is not public, then a `BeanGetterIsNotPublic` error will be generated.
   > This is done because programmers constantly forget that `BeanGetter` should be public,
   > and then they catch `NullPointerException` error and do not understand what's wrong for a long time.
   > Now an error is generated immediately, and it becomes clear what is wrong. 
 - `@LetBeNonePublic` annotation was added to make it possible to add `BeanGetter` with non-public access;

#### Changes 2.1.3 -> 2.1.5
 - Added Kotlin support
