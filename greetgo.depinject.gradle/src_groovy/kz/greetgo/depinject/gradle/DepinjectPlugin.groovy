package kz.greetgo.depinject.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.JavaExec

class DepinjectPlugin implements Plugin<Project> {

  @Override
  void apply(Project project) {
    project.task("hi") {
      doLast {
        println "Hi to everybody"
      }
    }

    project.task('generate', type: JavaExec) {
      doFirst {
        def javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention)
        classpath javaPluginConvention.getSourceSets().findByName("test").getRuntimeClasspath()
      }

      main = 'kz.greetgo.depinject.gen.DepinjectGenerate'
      args = ['impl', '-p', 'kz.greetgo.tests', '-s', project.buildDir.getAbsolutePath() + "/depinject_generated_src"]
    }

  }

}
