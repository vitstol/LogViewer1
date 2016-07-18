name := "LogViewer"

version := "1.0"

lazy val `logviewer` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( jdbc , cache , ws   , specs2 % Test ,
  "org.hibernate" % "hibernate-core" % "4.3.8.Final",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.8.Final",
  "mysql" % "mysql-connector-java" % "5.1.37",
  "joda-time" % "joda-time" % "2.9.1",
  "net.sourceforge.jtds" % "jtds" % "1.2",
  "org.joda" % "joda-convert" % "1.6",
  "com.typesafe.slick" % "slick_2.11" % "2.1.0",
  "com.github.tototoshi" %% "slick-joda-mapper" % "1.2.0",
  "com.typesafe.play" %% "play-slick" % "0.8.0",
  "org.springframework" % "spring-context" % "4.2.3.RELEASE",
  "org.springframework" % "spring-context-support" % "4.2.3.RELEASE",
  "org.springframework.data" % "spring-data-jpa" % "1.9.1.RELEASE",
  "org.psnively" %% "spring_scala_4-2-0" % "1.0.0",
  "org.springframework" % "spring-webmvc" % "4.0.0.RELEASE"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"