package uk.ac.ncl.openlab.intake24.api.client

import java.io.{BufferedReader, InputStreamReader}

import io.circe.generic.auto._

object ApiConfigChooser extends JsonCodecs {

  val default = IndexedSeq("Development" -> "development-api-config.json", "Test" -> "test-api-config.json", "Production" -> "production-api-config.json")

  def getDevelopmentApiConfiguration(configDirRelativePath: String = "./api-config") = fromJson[ApiConfiguration](scala.io.Source.fromFile(configDirRelativePath + "/" + "development-api-config.json").mkString).right.get

  def chooseApiConfiguration(configDirPath: String = "./api-config", message: String = "Please choose the API instance for this operation:", options: IndexedSeq[(String, String)] = default): ApiConfiguration = {

    println()
    println(message)
    println()

    options.zipWithIndex.foreach {
      case ((name, path), index) =>
        println(s"${index + 1}. $name")
    }

    val reader = new BufferedReader(new InputStreamReader(System.in))

    var choice: Option[Int] = None

    while (choice.isEmpty) {
      val input = reader.readLine()

      try {
        val in = input.toInt
        if (in >0 && in <= options.size)
          choice = Some(in - 1)
        else
          choice = None
      } catch {
        case e: NumberFormatException => choice = None
      }

      if (choice.isEmpty)
        println (s"Please enter a number (1 to ${options.size}) or press Control+C to abort.")
    }

    val start: Option[Int] = Some(1)

    println()

    fromJson[ApiConfiguration](scala.io.Source.fromFile(configDirPath + "/" + options(choice.get)._2).mkString).right.get
  }
}
