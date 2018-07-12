package kz.greetgo.depinject.gradle

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class DepinjectPluginFunctionalTest extends Specification {
  @Rule
  final TemporaryFolder testProjectDir = new TemporaryFolder()
  File buildFile
  List<File> pluginClasspath

  def setup() {
    buildFile = testProjectDir.newFile('build.gradle')

    def pluginClasspathResource = getClass().classLoader.findResource("plugin-classpath.txt")
    if (pluginClasspathResource == null) {
      throw new IllegalStateException("Did not find plugin classpath resource, run `testClasses` build task.")
    }

    pluginClasspath = pluginClasspathResource.readLines().collect { new File(it) }
  }

  def "hello world task prints hello world"() {
    given:
    buildFile << """
      plugins {
        id 'kz.greetgo.depinject.plugin'
      }
      task helloWorld {
        doLast {
          println 'Hello world!'
        }
      }
    """

    when:
    def result = GradleRunner.create()
      .withProjectDir(testProjectDir.root)
      .withPluginClasspath(pluginClasspath)
      .withArguments('helloWorld')
      .build()

    println "buildFile = " + buildFile

    then:
    result.output.contains('Hello world!')
    result.task(":helloWorld").outcome == SUCCESS
  }
}
