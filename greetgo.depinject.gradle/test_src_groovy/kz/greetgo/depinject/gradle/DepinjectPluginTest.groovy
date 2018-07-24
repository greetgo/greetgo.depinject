package kz.greetgo.depinject.gradle

import org.gradle.testkit.runner.GradleRunner
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import java.nio.file.Path
import java.nio.file.Paths
import java.text.SimpleDateFormat

import static java.lang.Math.abs
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

//    testClasspath.collect {
//      println "--- test class path : " + it
//    }
  }

  Path testProjectDir
  String testProjectName

  File projectDir() {
    return testProjectDir.resolve(testProjectName).toAbsolutePath().toFile()
  }

  @BeforeMethod
  void initTestProjectDir() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS-")
    testProjectDir = Paths.get("build/projects-for-tests/pr-" + sdf.format(new Date()) + abs(random.nextInt()))
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
  void "depinject start"() {

    def testClasspathStr = testClasspath
      .collect { it.absolutePath.replace('\\', '\\\\') } // escape backslashes in Windows paths
      .collect { "'$it'" }
      .join(", ")

    newFile("build.gradle") << """
      plugins {
        id 'kz.greetgo.depinject.plugin'
      }

      apply plugin: 'java'

      version = '12.23.34'

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
        //dependsOn depinjectJar
        main = 'kz.greetgo.tests.run.Main'
        args = []
        classpath sourceSets.test.runtimeClasspath
        //classpath depinjectJar
      }

    """.stripIndent()

    new AntBuilder().copy(todir: dir("src").toPath()) {
      fileset(dir: depinjectGradle().resolve("sources_for_tests/test1"), includes: "**")
    }

    def result = GradleRunner.create()
      .withProjectDir(projectDir())
      .withPluginClasspath(pluginClasspath)
//      .withArguments('runTest')
//      .withArguments('depinjectCompile')
      .withArguments('depinjectJar')
      .build()

    println "result.output = [[" + result.output + "]]"

//    assertThat(result.output.contains('Hi to everybody')).isTrue()
    assertThat(result.task(":depinjectGenerate").outcome).isEqualTo(SUCCESS)
  }

}
