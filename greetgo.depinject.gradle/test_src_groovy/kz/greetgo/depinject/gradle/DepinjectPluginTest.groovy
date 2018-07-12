package kz.greetgo.depinject.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.text.SimpleDateFormat

import static java.lang.Math.abs
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING
import static modules.Modules.depinjectGradle
import static org.fest.assertions.api.Assertions.assertThat
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class DepinjectPluginTest {
  final Random random = new Random()

  List<File> pluginClasspath
  List<File> testClasspath

  @BeforeMethod
  void initClasspath() {
    URL pluginClasspathResource = getClass().classLoader.findResource("plugin-classpath.txt")
    if (pluginClasspathResource == null) {
      throw new IllegalStateException("Did not find plugin classpath resource, run `testClasses` build task.")
    }

    pluginClasspath = pluginClasspathResource.readLines().collect { new File(it) }

    URL testClasspathResource = getClass().classLoader.findResource("test-classpath.txt")
    if (testClasspathResource == null) {
      throw new IllegalStateException("Did not find test classpath resource, run `testClasses` build task.")
    }

    testClasspath = testClasspathResource.readLines().collect { new File(it) }
  }

  Path testProjectDir
  String testProjectName

  File projectDir() {
    return testProjectDir.resolve(testProjectName).toAbsolutePath().toFile()
  }

  @BeforeMethod
  void initTestProjectDir() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS-")
    testProjectDir = Paths.get("build/test_projects/pr-" + sdf.format(new Date()) + abs(random.nextInt()))
    testProjectName = "test"
  }

  File dir(String pathAndName) {
    return testProjectDir.resolve(testProjectName).resolve(pathAndName).toFile()
  }

  File newFile(String pathAndName) {
    def ret = dir(pathAndName)
    ret.getParentFile().mkdirs()
    return ret
  }

  @Test
  void "Check plugin name from properties"() {
    Project project = ProjectBuilder.builder().build()
    project.pluginManager.apply 'kz.greetgo.depinject.plugin'

    newFile("build.gradle") << """
      task asd {
        doLast {
          println 'Hi to all'
        }
      }
    """.stripIndent()
    newFile("asd/wow.txt") << """
      Привет
    """.stripIndent()

    BuildResult result = GradleRunner.create()
      .withProjectDir(projectDir())
      .withArguments("asd")
      .build()

    assertThat(result.task(":asd").getOutcome()).isEqualTo(SUCCESS)

    assertThat(result.output.contains("Hi to all")).isTrue()
  }

  @Test
  void "simple probe kz.greetgo.depinject.plugin"() {

    newFile("build.gradle") << """
      plugins {
        id 'kz.greetgo.depinject.plugin'
      }
    """.stripIndent()

    def result = GradleRunner.create()
      .withProjectDir(projectDir())
      .withPluginClasspath(pluginClasspath)
      .withArguments('hi')
      .build()

    println "result.output = [[" + result.output + "]]"
    println pluginClasspath

    assertThat(result.output.contains('Hi to everybody')).isTrue()
    assertThat(result.task(":hi").outcome).isEqualTo(SUCCESS)
  }

  @Test
  void "test java compilation"() {

    def testClasspathStr = testClasspath
      .collect { it.absolutePath.replace('\\', '\\\\') } // escape backslashes in Windows paths
      .collect { "'$it'" }
      .join(", ")

    newFile("build.gradle") << """
      apply plugin: 'java'
      
      dependencies {
        compile files($testClasspathStr)
      }

      [compileJava, compileTestJava]*.options*.encoding = 'UTF-8'
      [compileJava, compileTestJava]*.options*.debugOptions*.debugLevel = "source,lines,vars"

      sourceSets.main.java.srcDirs = ["src"]
      sourceSets.test.java.srcDirs = ["test_src"]
      sourceSets.main.resources.srcDirs = ["src_resources"]
      sourceSets.test.resources.srcDirs = ["test_resources"]

      sourceSets.main.resources {
        srcDirs += sourceSets.main.java.srcDirs
        exclude '**/*.java'
      }
      sourceSets.test.resources {
        srcDirs += sourceSets.test.java.srcDirs
        exclude '**/*.java'
      }

      task runTest(type: JavaExec) {
        main = 'kz.greetgo.tests.Test'
        args = []
        classpath sourceSets.test.runtimeClasspath
      }
    """.stripIndent()

    new AntBuilder().copy(todir: dir("src").toPath()) {
      fileset(dir: depinjectGradle().resolve("sources_for_tests/test1"), includes: "**")
    }

    newFile("src/kz/greetgo/tests/Test.java") << """
      package kz.greetgo.tests;

      public class Test {
        public static void main(String []args) {
          kz.greetgo.depinject.Depinject x;
          System.out.println("Hello World!!! --- from test");
        }
      }
    """.stripIndent()

    def result = GradleRunner.create()
      .withProjectDir(projectDir())
      .withPluginClasspath(pluginClasspath)
      .withArguments('runTest')
      .build()

    println "result.output = [[" + result.output + "]]"

//    assertThat(result.output.contains('Hi to everybody')).isTrue()
    assertThat(result.task(":runTest").outcome).isEqualTo(SUCCESS)
  }

  @Test
  void "test depinject"() {

    def testClasspathStr = testClasspath
      .collect { it.absolutePath.replace('\\', '\\\\') } // escape backslashes in Windows paths
      .collect { "'$it'" }
      .join(", ")

    newFile("build.gradle") << """
      apply plugin: 'java'
      
      dependencies {
        compile files($testClasspathStr)
      }

      [compileJava, compileTestJava]*.options*.encoding = 'UTF-8'
      [compileJava, compileTestJava]*.options*.debugOptions*.debugLevel = "source,lines,vars"

      sourceSets.main.java.srcDirs = ["src"]
      sourceSets.test.java.srcDirs = ["test_src"]
      sourceSets.main.resources.srcDirs = ["src_resources"]
      sourceSets.test.resources.srcDirs = ["test_resources"]

      task runTest(type: JavaExec) {
        main = 'kz.greetgo.tests.run.Main'
        args = []
        classpath sourceSets.test.runtimeClasspath
      }
    """.stripIndent()

    new AntBuilder().copy(todir: dir("src").toPath()) {
      fileset(dir: depinjectGradle().resolve("sources_for_tests/test1"), includes: "**")
    }

    def result = GradleRunner.create()
      .withProjectDir(projectDir())
      .withPluginClasspath(pluginClasspath)
      .withArguments('runTest')
      .build()

    println "result.output = [[" + result.output + "]]"

//    assertThat(result.output.contains('Hi to everybody')).isTrue()
    assertThat(result.task(":runTest").outcome).isEqualTo(SUCCESS)
  }

}
