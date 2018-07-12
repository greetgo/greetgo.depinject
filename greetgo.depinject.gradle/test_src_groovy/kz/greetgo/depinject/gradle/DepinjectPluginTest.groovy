package kz.greetgo.depinject.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.testng.annotations.Test

import java.nio.file.Path

import static org.fest.assertions.api.Assertions.assertThat

class DepinjectPluginTest {
  final Random random = new Random()

  Path createTempDir() {
    def dir = new File("build/test_projects/prg" + random.nextLong());
    dir.mkdirs()
    return dir.toPath()
  }

  @Test
  void "Check plugin name from properties"() {
    Project project = ProjectBuilder.builder().build()
    project.pluginManager.apply 'kz.greetgo.depinject.plugin'

    assertThat(project.name).isNotEqualTo("asd")

    def testProjectDir = createTempDir()
    testProjectDir.resolve("build.gradle").toFile() << "" +
      "task asd {" +
      "  doLast {" +
      "    println 'Hi to all'" +
      "  }" +
      "}"

    BuildResult result = GradleRunner.create()
      .withProjectDir(testProjectDir.toAbsolutePath().toFile())
      .withArguments("asd")
      .build()

    assertThat(result.task(":asd").getOutcome()).isEqualTo(TaskOutcome.SUCCESS)

    assertThat(result.output.contains("Hi to all")).isTrue()
  }
}
