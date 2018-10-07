package example

import java.io.File

import example.Json4s.FizzBuzzHolder
import org.json4s.jackson.Serialization.read
import org.scalatest.FlatSpec
import sbt.io.IO

class Json4sSpec extends FlatSpec {

  def createSourceJSONAndThenFizzBuzzFromJSON(n: Int): Unit = {
    implicit val formats = org.json4s.DefaultFormats
    val sourceFile = new File("sample.json")
    val destinationFile = new File("fizzBuzz.json")

    Json4s.createSourceJSON(n, sourceFile)
    Json4s.fizzBuzzFromJSON(sourceFile, destinationFile)

    val json = read[FizzBuzzHolder](IO.read(destinationFile))
    json.fizzBuzz.zipWithIndex.foreach(pair => {
      pair._2 + 1 match {
        case x if x % 15 == 0 => assert(pair._1 === "FizzBuzz")
        case x if x % 3 == 0 => assert(pair._1 === "Fizz")
        case x if x % 5 == 0 => assert(pair._1 === "Buzz")
        case x => assert(pair._1 === x.toString)
      }
    })
  }

  s"'createSourceJSON' & 'fizzBuzzFromJSON' (1 to 0)" should
    "throw IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      createSourceJSONAndThenFizzBuzzFromJSON(0)
    }
  }
}
