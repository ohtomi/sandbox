package example

object Basic {

  def test0(): Unit = {
    val message = "Hello World"
    println(message)
  }

  def identity(x: Int): Int = x

  def noarg1 = 4567

  def noarg2() = 7890

  def test1(): Unit = {
    val int0 = 1234
    println(identity(int0))
    val int1 = noarg1
    println(identity(int1))
    val int2 = noarg2()
    println(identity(int2))
  }

  def fizzBuzz(n: Int): Unit = {
    for (i <- (1 to n)) {
      val r = if (i % 15 == 0) {
        "FizzBuzz"
      } else if (i % 3 == 0) {
        "Fizz"
      } else if (i % 5 == 0) {
        "Buzz"
      } else {
        i
      }
      println(r)
    }
  }

  def fizzBuzz2(n: Int): Unit = {
    for (i <- (1 until n + 1 by 1)) {
      i match {
        case x if x % 15 == 0 => println("FizzBuzz")
        case x if x % 3 == 0 => println("Fizz")
        case x if x % 5 == 0 => println("Buzz")
        case x => println(x)
      }
    }
  }

  def matcher(n: Int): String = n match {
    case 0 => "0だよ"
    case 1 | 2 => "1か2ですね"
    case x if x % 3 == 0 => "3で割り切れる"
    case x => s"それ以外の数値 ${x} でした"
  }

  def test2(): Unit = {
    fizzBuzz(16)
    fizzBuzz2(16)

    println(matcher(0))
    println(matcher(1))
    println(matcher(3))
    println(matcher(4))
  }

  def fizzBuzz3(n: Int, i: Int = 1): Unit = {
    i match {
      case x if x % 15 == 0 => println("FizzBuzz")
      case x if x % 3 == 0 => println("Fizz")
      case x if x % 5 == 0 => println("Buzz")
      case x => println(x)
    }
    if (i < n) {
      fizzBuzz3(n, i + 1)
    }
  }

  def test3(): Unit = {
    fizzBuzz3(16)
  }

  def fib(n: Int): Int = {
    @scala.annotation.tailrec
    def go(n: Int, prev: Int, curr: Int): Int =
      if (n == 0) prev
      else go(n - 1, curr, prev + curr)

    go(n, 0, 1)
  }

  def test4(): Unit = {
    println(fib(0))
    println(fib(1))
    println(fib(2))
    println(fib(3))
    println(fib(4))
    println(fib(5))
    println(fib(10))
  }

  case class SpecialInt(i: Int) {

    def !+!(v: Int): SpecialInt = new SpecialInt(i + v)

    def !-!(v: Int): SpecialInt = new SpecialInt(i - v)
  }

  def test5(): Unit = {
    val i = new SpecialInt(12345)
    val j = i !+! 5
    val k = i !-! 5
    val l = i !+! 3 !-! 2
    println(
      s"""i => $i
  j => $j
  k => $k
  l => $l""")
  }
}
