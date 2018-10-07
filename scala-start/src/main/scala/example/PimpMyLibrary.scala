package example

object PimpMyLibrary {

  implicit class MyString(val str: String) extends AnyVal {

    def addPeriod(): String =
      if (str.endsWith(".")) str else str + "."
  }

  def test0(): Unit = {
    println("Hello, world".addPeriod())
    println("I am a pen.".addPeriod())
  }
}
