package kz.greetgo.depinject.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

import java.nio.file.Files
import java.nio.file.Path

class DepinjectPlugin implements Plugin<Project> {

  @Override
  void apply(Project project) {
    println "Hello from project with name = '" + project.name + "'"

  }

}
