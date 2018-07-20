package kz.greetgo.depinject.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class DepinjectPlugin implements Plugin<Project> {

  @Override
  void apply(Project project) {
    project.task("hi") {
      doLast {
        println "Hi to everybody 3456"
      }
    }
  }

}
