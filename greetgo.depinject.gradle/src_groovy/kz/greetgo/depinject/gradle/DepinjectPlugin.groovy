package kz.greetgo.depinject.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.FileCollection
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.configuration.project.ProjectConfigurationActionContainer

import javax.inject.Inject

class DepinjectPlugin implements Plugin<Project> {

  private final ProjectConfigurationActionContainer configurationActionContainer

  private static FileCollection getTestClasspath(Project project) {
    JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention)
    return javaPluginConvention.getSourceSets().findByName("test").getRuntimeClasspath()
  }

  @Inject
  DepinjectPlugin(ProjectConfigurationActionContainer configurationActionContainer) {
    this.configurationActionContainer = configurationActionContainer
  }

  @Override
  void apply(Project project) {
    project.task("hi") {
      doLast {
        println "Hi to everybody"
      }
    }

    configurationActionContainer.add(new Action<Project>() {
      void execute(Project p) {
        applyAction(p)
      }
    })

  }

  private static File buildResolve(Project project, String sub) {
    return project.buildDir.toPath().resolve(sub).toFile()
  }

  private static Jar getJar(Project project) {
    Set<Task> jarTaskSet = project.getTasksByName("jar", false)
    if (jarTaskSet.isEmpty()) throw new RuntimeException("No task jar")
    if (jarTaskSet.size() > 1) throw new RuntimeException("Too many jar tasks")
    //noinspection ChangeToOperator
    return jarTaskSet.iterator().next() as Jar;
  }

  void applyAction(Project project) {
    Task generateTask = project.task('depinjectGenerate', type: JavaExec) {
      classpath getTestClasspath(project)
      main = 'kz.greetgo.depinject.gen.DepinjectGenerate'
      args = ['impl',
              '-p', 'kz.greetgo.tests',
              '-s', buildResolve(project, "depinject_generated_src").getAbsolutePath()
      ]
    }

    Task compileTask = project.task('depinjectCompile', type: JavaCompile) {
      dependsOn generateTask
      classpath = getTestClasspath(project)
      source = buildResolve(project, "depinject_generated_src")
      destinationDir = buildResolve(project, "depinject_generated_classes")
    }

    project.task("depinjectJar", type: Jar) {
      dependsOn compileTask
      baseName getJar(project).getBaseName() + "-depinject"
      from buildResolve(project, "depinject_generated_classes")
    }
  }
}
