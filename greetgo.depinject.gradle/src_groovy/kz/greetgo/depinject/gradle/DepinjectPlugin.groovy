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

  private static FileCollection getClasspath(Project project, String sourceSetName) {
    JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention)
    return javaPluginConvention.getSourceSets().findByName(sourceSetName).getRuntimeClasspath()
  }

  @Inject
  DepinjectPlugin(ProjectConfigurationActionContainer configurationActionContainer) {
    this.configurationActionContainer = configurationActionContainer
  }

  JavaExec depinjectGenerateSrcTask
  JavaCompile depinjectClassesTask
  Jar depinjectJarTask

  JavaExec depinjectGenerateTestSrcTask
  JavaCompile depinjectTestClassesTask

  @Override
  void apply(Project project) {
    depinjectGenerateSrcTask = project.task('depinjectGenerateSrc', type: JavaExec) as JavaExec
    depinjectClassesTask = project.task('depinjectClasses', type: JavaCompile) as JavaCompile
    depinjectJarTask = project.task('depinjectJar', type: Jar) as Jar

    depinjectGenerateTestSrcTask = project.task('depinjectGenerateTestSrc', type: JavaExec) as JavaExec
    depinjectTestClassesTask = project.task('depinjectTestClasses', type: JavaCompile) as JavaCompile

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
  private static <T extends Task> T getTask(Project project, String taskName, Class<T> ignore) {
    Set<Task> jarTaskSet = project.getTasksByName(taskName, false)
    if (jarTaskSet.isEmpty()) throw new RuntimeException("No task " + taskName)
    if (jarTaskSet.size() > 1) throw new RuntimeException("Too many " + taskName + " tasks")
    //noinspection ChangeToOperator
    return jarTaskSet.iterator().next() as T
  }

  @SuppressWarnings("GrMethodMayBeStatic")
  void applyAction(Project project) {

    depinjectGenerateSrcTask.classpath getClasspath(project, "test")
    depinjectGenerateSrcTask.main = 'kz.greetgo.depinject.gen.DepinjectGenerate'
    depinjectGenerateSrcTask.args = ['impl',
                                     '-p', 'kz.greetgo.tests',
                                     '-s', buildResolve(project, "depinject_generated_src").getAbsolutePath()]

    depinjectClassesTask.dependsOn depinjectGenerateSrcTask
    depinjectClassesTask.classpath = getClasspath(project, "main")
    depinjectClassesTask.source = buildResolve(project, "depinject_generated_src")
    depinjectClassesTask.destinationDir = buildResolve(project, "depinject_classes")

    depinjectJarTask.dependsOn depinjectClassesTask
    depinjectJarTask.baseName = getTask(project, 'jar', Jar).getBaseName() + "-depinject"
    depinjectJarTask.from buildResolve(project, "depinject_classes")

    getTask(project, "build", Task).dependsOn depinjectJarTask

    // * * * * * * for test env

    depinjectGenerateTestSrcTask.dependsOn depinjectClassesTask
    depinjectGenerateTestSrcTask.classpath getClasspath(project, "test")
    depinjectGenerateTestSrcTask.main = 'kz.greetgo.depinject.gen.DepinjectGenerate'
    depinjectGenerateTestSrcTask.args = ['impl',
                                         '-p', 'kz.greetgo.tests',
                                         '-s', buildResolve(project, "depinject_generated_test_src").getAbsolutePath()]

    depinjectTestClassesTask.dependsOn depinjectGenerateTestSrcTask
    depinjectTestClassesTask.classpath = getClasspath(project, "test")
    depinjectTestClassesTask.source = buildResolve(project, "depinject_generated_test_src")
    depinjectTestClassesTask.destinationDir = buildResolve(project, "depinject_test_classes")
  }
}
