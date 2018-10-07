package example

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

object Futures {

  def test0(): Unit = {
    val f1 = Future {
      Thread.sleep(2000)
      println("task1 has done.")
      4 / 2
    }
    val f2 = Future {
      Thread.sleep(500)
      println("task2 has done.")
      2 / 0
    }

    val f = f1.zip(f2)
    f.onComplete {
      case Success(v) => println(s"v => ${v._1 + v._2}")
      case Failure(e) => println(s"e => ${e.getMessage}")
    }
  }
}
