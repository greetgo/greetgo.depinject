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

  JavaExec generateTask
  JavaCompile compileTask
  Jar depinjectJar

  @Override
  void apply(Project project) {
    generateTask = project.task('depinjectGenerate', type: JavaExec)    as JavaExec
    compileTask  = project.task('depinjectCompile',  type: JavaCompile) as JavaCompile
    depinjectJar = project.task('depinjectJar',      type: Jar)         as Jar

    configurationActionContainer.add(new Action<Project>() {
      void execute(Project p) {
        applyAction(p)
      }
    })
  }

  private static File buildResolve(Project project, String sub) {
    return project.buildDir.toPath().resolve(sub).toFile()
  }

  @SuppressWarnings("GroovyUnusedDeclaration")
  private static <T extends Task> T getJar(Project project, Class<T> ignore) {
    Set<Task> jarTaskSet = project.getTasksByName("jar", false)
    if (jarTaskSet.isEmpty()) throw new RuntimeException("No task jar")
    if (jarTaskSet.size() > 1) throw new RuntimeException("Too many jar tasks")
    //noinspection ChangeToOperator
    return jarTaskSet.iterator().next() as T
  }

  @SuppressWarnings("GrMethodMayBeStatic")
  void applyAction(Project project) {

    generateTask.classpath getTestClasspath(project)
    generateTask.main = 'kz.greetgo.depinject.gen.DepinjectGenerate'
    generateTask.args = ['impl',
                         '-p', 'kz.greetgo.tests',
                         '-s', buildResolve(project, "depinject_generated_src").getAbsolutePath()]

    compileTask.dependsOn generateTask
    compileTask.classpath = getTestClasspath(project)
    compileTask.source = buildResolve(project, "depinject_generated_src")
    compileTask.destinationDir = buildResolve(project, "depinject_generated_classes")

    depinjectJar.dependsOn compileTask
    depinjectJar.baseName = getJar(project, Jar).getBaseName() + "-depinject"
    depinjectJar.from buildResolve(project, "depinject_generated_classes")
  }
}
