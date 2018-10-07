package example

object Tuple {

  def popHead(xs: List[Int]): (Int, List[Int]) = xs.head -> xs.tail

  def test0(): Unit = {
    val t1 = (123, "hello")
    println(s"t1 => ${t1}, t1[0] => ${t1._1}")

    val t2 = 345 -> "hello"
    println(s"t2 => ${t2}, t2[0] => ${t2._1}")

    val t3 = Tuple2[Int, String](567, "hello")
    println(s"t3 => ${t3}, t3[0] => ${t3._1}")
  }

  def test1(): Unit = {
    val t1 = (123, "hello")
    println(s"t1 => ${t1}, t1[0] => ${t1._1}")
    // t1._1 = t1._1 * 10
    // reassignment to val
  }

  def test2(): Unit = {
    val t1 = (1, 2, 3, 4)
    val t2 = Tuple4[Int, Int, Int, Int](5, 6, 7, 8)
    val t3 = new Tuple4[Int, Int, Int, Int](9, 0, 1, 2)
    val t4 = new (Int, Int, Int, Int)(3, 4, 5, 6)
    println(s"t1 => $t1, t2 => $t2, t3 => $t3, t4 => $t4")
    val t5 = 1 -> 2 -> 3 -> 4
    println(s"t5 => $t5")
  }

  def test3(): Unit = {
    val (h, t) = popHead(List(3, 2, 1))
    println(s"h => $h, t => $t")
  }
}
