package example

trait A {
  val aaa: Int
}

trait A1 extends A {
  override val aaa = 123
  val a11 = 111
  val a12 = 111222333
}

trait A2 extends A {
  override val aaa = 345
  // val a11 = 333          // class AHolder inherits conflicting members
  // override val a11 = 333 // value a11 overrides nothing
}

trait A3 extends A {
  override val aaa = 567
}

trait A4 extends A {
  override val aaa = 789
  // val a11 = 777          // <$anon: example.AHolder with example.A4> inherits conflicting members
  // override val a11 = 777 // value a11 overrides nothing

  // val hhh = 777          // <$anon: example.AHolder with example.A4> inherits conflicting members
  // override val hhh = 777 // value hhh overrides nothing
}

case class AHolder() extends A1 with A2 with A3 {

  val hhh = 0
}

object Trait {

  def test0(): Unit = {
    val h1 = new AHolder()
    println(s"h1.aaa => ${h1.aaa}, h1.a11 => ${h1.a11}, h1.a12 => ${h1.a12}")
    val h2 = new AHolder() with A4
    println(s"h2.aaa => ${h2.aaa}")
  }
}
