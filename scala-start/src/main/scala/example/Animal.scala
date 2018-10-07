package example

sealed abstract class Animal(val cry: String)

case object Cat extends Animal("にゃー")

case object Dog extends Animal("わん")

object Animal {

  def checkAnimal(animal: Animal): Unit = animal match {
    case Cat =>
      println(s"!! 😺 !! ${animal} ${animal.cry}")
    case Dog =>
      println(s"!! 🐩 !! ${animal} ${animal.cry}")
  }

  def test0(): Unit = {
    val c = Cat
    Animal.checkAnimal(c)
    val d = Dog
    Animal.checkAnimal(d)
  }
}
