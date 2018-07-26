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

  private static FileCollection getCompileClasspath(Project project, String sourceSetName) {
    JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention)
    return javaPluginConvention.getSourceSets().findByName(sourceSetName).getCompileClasspath()
  }

  private static FileCollection getOutput(Project project, String sourceSetName) {
    JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention)
    return javaPluginConvention.getSourceSets().findByName(sourceSetName).getOutput()
  }

  @Inject
  DepinjectPlugin(ProjectConfigurationActionContainer configurationActionContainer) {
    this.configurationActionContainer = configurationActionContainer
  }


  @Override
  void apply(Project project) {


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

  private static File getGeneratedSrcDir(Project project) {
    return buildResolve(project, "depinject_generated_src")
  }

  private static File getGeneratedTestSrcDir(Project project) {
    return buildResolve(project, "depinject_generated_test_src")
  }

  private static File getClassesDir(Project project) {
    return buildResolve(project, "depinject_classes")
  }

  private static File getTestClassesDir(Project project) {
    return buildResolve(project, "depinject_test_classes")
  }

  @SuppressWarnings("GrMethodMayBeStatic")
  void applyAction(Project project) {

    JavaExec generateSrcTask = project.task('depinjectGenerateSrc', type: JavaExec) as JavaExec
    generateSrcTask.dependsOn getTask(project, "compileJava", Task)
    generateSrcTask.classpath getCompileClasspath(project, "test").getFiles()
    generateSrcTask.main = 'kz.greetgo.depinject.gen.DepinjectGenerate'
    generateSrcTask.args = ['impl',
                            '-p', 'kz.greetgo.tests',
                            '-s', getGeneratedSrcDir(project).getAbsolutePath()]

    JavaCompile compileTask = project.task('depinjectCompile', type: JavaCompile) as JavaCompile
    compileTask.dependsOn generateSrcTask
    compileTask.classpath = getCompileClasspath(project, "main")
    compileTask.source = getGeneratedSrcDir(project)
    compileTask.destinationDir = getClassesDir(project)
    compileTask.doLast {
      JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention)
      javaPluginConvention.getSourceSets().findByName("main").getOutput().dir(buildResolve(project, "depinject_classes"))
    }

    getTask(project, "classes", Task).dependsOn compileTask

    Jar jarTask = project.task('depinjectJar', type: Jar) as Jar
    jarTask.dependsOn compileTask
    jarTask.baseName = getTask(project, 'jar', Jar).getBaseName() + "-depinject"
    jarTask.from getClassesDir(project)

    getTask(project, "assemble", Task).dependsOn jarTask

    // * * * * * * for test env

    JavaExec generateTestSrcTask = project.task('depinjectGenerateTestSrc', type: JavaExec) as JavaExec
    generateTestSrcTask.dependsOn getTask(project, "compileTestJava", Task)
    generateTestSrcTask.classpath getCompileClasspath(project, "test").getFiles()
    generateTestSrcTask.main = 'kz.greetgo.depinject.gen.DepinjectGenerate'
    generateTestSrcTask.args = ['impl',
                                '-p', 'kz.greetgo.tests',
                                '-s', getGeneratedTestSrcDir(project).getAbsolutePath()]

    JavaCompile testCompileTask = project.task('depinjectTestCompile', type: JavaCompile) as JavaCompile
    testCompileTask.dependsOn generateTestSrcTask
    testCompileTask.classpath = getCompileClasspath(project, "test")
    testCompileTask.source = getGeneratedTestSrcDir(project)
    testCompileTask.destinationDir = getTestClassesDir(project)
    testCompileTask.doLast {
      JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention)
      javaPluginConvention.getSourceSets().findByName("test").getOutput().dir(getTestClassesDir(project))
    }

    getTask(project, "testClasses", Task).dependsOn testCompileTask
  }
}
