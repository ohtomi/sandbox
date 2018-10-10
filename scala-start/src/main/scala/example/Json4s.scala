package example

import sbt.io.IO
import java.io.File
import org.json4s.jackson.Serialization.{read, write}

object Json4s {

  implicit val formats = org.json4s.DefaultFormats

  def createSourceJSON(n: Int, srcFile: File): Unit = {
    require(n >= 1)

    val intArrayHolder = IntArrayHolder((1 to n).toArray)
    IO.write(srcFile, write(intArrayHolder))
  }

  case class IntArrayHolder(intArray: Array[Int])

  case class FizzBuzzHolder(fizzBuzz: Array[String])

  def fizzBuzzFromJSON(srcFile: File, dstFile: File): Unit = {
    val rawJson = IO.read(srcFile)
    val intArrayHolder = read[IntArrayHolder](rawJson)

    val fizzBuzz = intArrayHolder.intArray.map(i =>
      i match {
        case x if x % 15 == 0 => "FizzBuzz"
        case x if x % 3 == 0 => "Fizz"
        case x if x % 5 == 0 => "Buzz"
        case x => x.toString
      }
    )

    val fizzBuzzHolder = FizzBuzzHolder(fizzBuzz)
    IO.write(dstFile, write(fizzBuzzHolder))
  }

  def test0(): Unit = {
    val sourceFile = new File("sample.json")
    val destinationFile = new File("fizzBuzz.json")

    createSourceJSON(15, sourceFile)
    fizzBuzzFromJSON(sourceFile, destinationFile)
  }
}
