package example

abstract class Polygon(edges: List[Int]) {

  val n = edges.length
  val area: Double
}

class Triangle(edges: List[Int]) extends Polygon(edges) {

  val a = edges(0)
  val b = edges(1)
  val c = edges(2)

  // val n = 3 // value n needs `override' modifier
  val area = {
    val s = (a + b + c) / 2.0
    math.sqrt(s * (s - a) * (s - b) * (s - c))
  }
}

object Triangle {

  def fromEdges(edges: List[Int]): Either[String, Triangle] =
    if (edges.length != 3)
      Left(s"num of edges must be 3")
    else if (!(edges(0) + edges(1) > edges(2)
      && edges(1) + edges(2) > edges(0)
      && edges(2) + edges(0) > edges(1)))
      Left(s"invalid egeds")
    else
      Right(new Triangle(edges))
}

class Square(val edges: List[Int]) extends Polygon(edges) {

  val area = 1000
}

object Square {

  def fromEdges(edges: List[Int]): Either[String, Square] = Right(new Square(edges))
}

object Polygon {

  def fromEdges(edges: List[Int]): Polygon =
    edges.length match {
      case 3 => new Triangle(edges)
      case 4 => new Square(edges)
      case x => ???
    }

  def test0(): Unit = {
    val t = Polygon.fromEdges(List(3, 4, 5))
    println(s"t.area => ${t.area}")
  }

  def test1(): Unit = {
    val s = Polygon.fromEdges(List(3, 4, 5, 6))
    println(s"s.edges => ${s.asInstanceOf[Square].edges}")

    val _ = Polygon.fromEdges(List(3, 4, 5))
    // println(s"t.edges => ${t.asInstanceOf[Triangle].edges}")
    // value edges is not a member of example.Triangle
  }

  def test2(): Unit = {
    val t = Polygon.fromEdges(List(3, 4, 5))
    println(s"t.a => ${t.asInstanceOf[Triangle].a}")
    // t.asInstanceOf[Triangle].a = t.asInstanceOf[Triangle].a + 1
    // reassignment to val
  }

  def maybeSafeFromEdges(edges: List[Int]): Option[Polygon] =
    edges.length match {
      case 3 => Some(new Triangle(edges))
      case 4 => Some(new Square(edges))
      case x => None
    }

  def test3(): Unit = {
    val ps = List(
      Polygon.maybeSafeFromEdges(List(3, 4, 5)),
      Polygon.maybeSafeFromEdges(List(3, 4, 5, 6)),
      Polygon.maybeSafeFromEdges(List(3, 4, 5, 6, 7))
    )
    ps.foreach(it => it match {
      case Some(p) => println(s"p has ${p.n} edges, p.area => ${p.area}")
      case None => println(s"p has ??? edges, p is None")
    })
    ps.zipWithIndex.foreach(it => it._1.foreach(p => println(s"ps[${it._2}] => $p")))
  }

  def safeFromEdges(edges: List[Int]): Either[String, Polygon] =
    edges.length match {
      case 3 => Triangle.fromEdges(edges)
      case 4 => Square.fromEdges(edges)
      case x => Left(s"not supported polygon: ${x} edges")
    }

  def test4(): Unit = {
    val ps = List(
      Polygon.safeFromEdges(List(3, 4, 5)),
      Polygon.safeFromEdges(List(3, 4, 5, 6, 7)),
      Polygon.safeFromEdges(List(3, 4, 10))
    )
    ps.foreach(it => it match {
      case Right(p) => println(s"p is $p")
      case Left(s) => println(s"p is None: cause of '$s'")
    })
    ps.zipWithIndex.foreach(it => it._1.foreach(p => println(s"ps[${it._2}] => $p")))
  }
}
