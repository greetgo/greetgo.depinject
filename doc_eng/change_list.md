
### Change list

#### Changes 2.0.0 -> 2.0.1

 - A check was made to be sure that BeanGetter was public;
   > If BeanGetter is not public, then a BeanGetterIsNotPublic error will be generated.
   > This is done because programmers constantly forget that BeanGetter should be public,
   > and then they catch NullPointerException error and for a long time do not understand what's wrong. Now an error is generated    
     immediately,
   > and immediately it becomes clear what is wrong.
 - @LetBeNonePublic annotation was added to make it possible to add BeanGetter with non-public access;
