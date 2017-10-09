#!/usr/bin/env bash

#надо скопировать весь текст этого файла как есть и вставить его в терминал с bash-ем

rm -rf depinject.fast_start
mkdir depinject.fast_start
cd depinject.fast_start
cat > build.gradle <<EOF

repositories {
  jcenter() //Baruch, hi
}

dependencies {
  ext.depinjectVersion = '2.0.0'

  compile "kz.greetgo.depinject:greetgo.depinject:$depinjectVersion"

  testCompile "kz.greetgo.depinject:greetgo.depinject.testng:$depinjectVersion"
}

EOF

