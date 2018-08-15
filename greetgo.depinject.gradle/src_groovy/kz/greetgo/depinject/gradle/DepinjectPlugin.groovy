package kz.greetgo.depinject.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.FileCollection
import org.gradle.api.internal.file.DefaultFileCollectionFactory
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.configuration.project.ProjectConfigurationActionContainer

import javax.inject.Inject

class DepinjectPlugin implements Plugin<Project> {

  private final ProjectConfigurationActionContainer configurationActionContainer

  private static FileCollection getCompileClasspath(Project project, String sourceSetName) {
    JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention)
    return javaPluginConvention.getSourceSets().findByName(sourceSetName).getCompileClasspath()
  }

  private static FileCollection getCompileClasspathWithRuntime(Project project, String sourceSetName) {
    JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention)
    FileCollection retClasspath = javaPluginConvention.getSourceSets().findByName(sourceSetName).getCompileClasspath()

    JavaCompile compileTask = getTask(project, "compileJava", JavaCompile)

    def destinationDirCol = new DefaultFileCollectionFactory().fixed("additional", compileTask.destinationDir)

    return retClasspath + destinationDirCol
  }

  @Inject
  DepinjectPlugin(ProjectConfigurationActionContainer configurationActionContainer) {
    this.configurationActionContainer = configurationActionContainer
  }

  DepinjectPluginExt pluginExt

  @Override
  void apply(Project project) {

    pluginExt = project.extensions.create("depinject", DepinjectPluginExt)

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

  private File getGeneratedSrcDir(Project project) {
    return buildResolve(project, pluginExt.depinjectGeneratedSrc)
  }

  private File getGeneratedTestSrcDir(Project project) {
    return buildResolve(project, pluginExt.depinjectGeneratedTestSrc)
  }

  private File getClassesDir(Project project) {
    return buildResolve(project, pluginExt.depinjectClasses)
  }

  private File getTestClassesDir(Project project) {
    return buildResolve(project, pluginExt.depinjectTestClasses)
  }

  @SuppressWarnings("GrMethodMayBeStatic")
  void applyAction(Project project) {

    JavaExec generateSrcTask = project.task('depinjectGenerateSrc', type: JavaExec) as JavaExec
    generateSrcTask.dependsOn getTask(project, "compileJava", Task)
    generateSrcTask.classpath getCompileClasspath(project, "test").getFiles()
    generateSrcTask.doFirst { pluginExt.checkPackages() }
    generateSrcTask.main = 'kz.greetgo.depinject.gen.DepinjectGenerate'
    generateSrcTask.args = ['impl',
                            '-p', pluginExt.packagesColon(),
                            '-s', getGeneratedSrcDir(project).getAbsolutePath()]

    JavaCompile compileTask = project.task('depinjectCompile', type: JavaCompile) as JavaCompile
    compileTask.dependsOn generateSrcTask
    compileTask.classpath = getCompileClasspathWithRuntime(project, "main")
    compileTask.source = getGeneratedSrcDir(project)
    compileTask.destinationDir = getClassesDir(project)
    compileTask.doLast {
      JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention)
      javaPluginConvention.getSourceSets().findByName("main").getOutput().dir(buildResolve(project, "depinject_classes"))
    }

    getTask(project, "classes", Task).dependsOn compileTask

    Jar jarTask = project.task('depinjectJar', type: Jar) as Jar
    jarTask.dependsOn compileTask
    jarTask.baseName = getTask(project, 'jar', Jar).getBaseName() + pluginExt.depinjectJarPostfix
    jarTask.from getClassesDir(project)

    getTask(project, "assemble", Task).dependsOn jarTask

    // * * * * * * for test env

    JavaExec generateTestSrcTask = project.task('depinjectGenerateTestSrc', type: JavaExec) as JavaExec
    generateTestSrcTask.dependsOn getTask(project, "compileTestJava", Task)
    generateTestSrcTask.classpath getCompileClasspath(project, "test").getFiles()
    generateTestSrcTask.main = 'kz.greetgo.depinject.gen.DepinjectGenerate'
    generateTestSrcTask.args = ['impl',
                                '-p', pluginExt.packagesColon(),
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
