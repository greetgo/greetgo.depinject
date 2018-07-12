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

  def "some probes"() {
    given:
    buildFile << """
      plugins {
        id 'kz.greetgo.depinject.plugin'
      }
    """

    testProjectDir.newFolder("asd").createNewFile()

    when:
    def result = GradleRunner.create()
      .withProjectDir(testProjectDir.root)
      .withPluginClasspath(pluginClasspath)
      .withArguments('hi')
      .build()

    println "result.output = [[" + result.output + "]]"

    println "buildFile = " + buildFile

    then:
    result.output.contains('Hi to everybody')
    result.task(":hi").outcome == SUCCESS
  }
}
