import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "smallCRM"
    val appVersion      = "dev"

    val appDependencies = Seq(
      // Add your project dependencies here,
    )

	val main = PlayProject(appName, appVersion, mainLang = JAVA)

}
