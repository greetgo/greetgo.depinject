 #!/usr/bin/env bash
 #                                                                                                 ▲
 # Надо скопировать весь текст этого файла как есть и вставить его в терминал с bash-ем, для этого:│
 # 1. надо нажать на кнопку Raw здесь ─────────────────────────────────────────────────────────────┘
 # 2. нажать Ctrl+A потом нажать Ctrl+Ins
 # 3. перенести фокус в терминал с bash-ем
 # 4. нажать Shift+Ins

 rm -rf depinject.fast_start
 mkdir depinject.fast_start
 cd depinject.fast_start
 cat > build.gradle <<EOF

apply plugin: 'java'
apply plugin: 'maven'
apply plugin: 'idea'

repositories {
  jcenter() //Baruch, hi
}

dependencies {
  ext.depinjectVersion = '2.0.0'
  ext.testNgVersion = '6.5.1'

  compile "kz.greetgo.depinject:greetgo.depinject:\$depinjectVersion"

  testCompile "kz.greetgo.depinject:greetgo.depinject.testng:\$depinjectVersion"

  testCompile "org.testng:testng:\$testNgVersion"
  testCompile 'org.easytesting:fest-assert-core:2.0M10'
}

test { useTestNG() }

EOF

 mkdir -p src/main/java/depinject/fast_start
 mkdir -p src/test/java/depinject/fast_start

 cat > src/main/java/depinject/fast_start/BeanConfigHelloWorld.java <<EOF
package depinject.fast_start;

import kz.greetgo.depinject.core.*;

@BeanConfig
@BeanScanner
public class BeanConfigHelloWorld {}
EOF
 cat > src/main/java/depinject/fast_start/Hello.java <<EOF
package depinject.fast_start;

import kz.greetgo.depinject.core.*;

@Bean
public class Hello {

  public String message() {
    return "Hello";
  }

}
EOF
 cat > src/main/java/depinject/fast_start/World.java <<EOF
package depinject.fast_start;

import kz.greetgo.depinject.core.*;

@Bean
public class World {

  public String message() {
    return "World";
  }

}
EOF
 cat > src/main/java/depinject/fast_start/HelloWorld.java <<EOF
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
EOF
 cat > src/test/java/depinject/fast_start/HelloWorldTest.java <<EOF
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
EOF

 # OK
