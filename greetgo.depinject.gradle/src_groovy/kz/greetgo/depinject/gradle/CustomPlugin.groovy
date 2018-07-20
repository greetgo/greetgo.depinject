package kz.greetgo.depinject.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec

class CustomPlugin implements Plugin<Project> {
  public static void main(String[] args) {

  }
  @Override
  void apply(Project project) {

    project.task('generate', type: JavaExec) {
      main = 'some.package.ClassWithMainInTestScope'
      args = ['some', 'arguments']
      classpath project.sourceSets.test.runtimeClasspath
    }

  }
}

