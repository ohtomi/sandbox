package example

case class Box[T](var element: T) {

    def get(): T = element
    def set(newElement: T): Unit = {
        element = newElement
    }
}

object Collection {

    def test0(): Unit = {
        val box1 = new Box[Int](10)
        println(s"box1 => $box1")

        println(s"box1.get() => ${box1.get}")
        box1.set(0)
        println(s"box1.get() => ${box1.get}")

        val box2 = new Box[Animal](Cat)
        println(s"box2 => $box2")

        println(s"box2.get() => ${box2.get}")
        box2.set(Dog)
        println(s"box2.get() => ${box2.get}")

        val box3 = new Box(Cat)
        println(s"box3 => $box3") // box3 is Box[Cat]

        // println(s"box3.get() => ${box3.get}")
        // box3.set(Dog) // type mismatch
        // println(s"box3.get() => ${box3.get}")
    }

    def toFizzBuzz(numbers: List[Int]): List[String] = numbers.map((i: Int) => i match {
        case x if x % 15 == 0 => "FizzBuzz"
        case x if x % 3 == 0 => "Fizz"
        case x if x % 5 == 0 => "Buzz"
        case x => x.toString
    })

    def test1(): Unit = {
        val numbers = (1 to 15).toList
        val fizzBuzzList = toFizzBuzz(numbers)
        fizzBuzzList.foreach(println)
    }

    def mapTR[T, R](t: T, r: R): (T, R) = (t, r)

    def toTupleOfTR[T, R](list: List[T], r: R): List[Tuple2[T, R]] = {
        val f: (T, R) => Tuple2[T, R] = mapTR[T, R]
        list.map(t => f(t, r))
    }

    def test2(): Unit = {
        val numbers = (1 to 3).toList
        val r = "ABCDE"
        val tupleList = toTupleOfTR(numbers, r)
        tupleList.foreach((t: (Int, String)) => println(t))
    }

    def test3(): Unit = {
        "hello".foreach(println)
    }

    def test4(): Unit = {
        val arr = Array("hello", "world")
        println(arr(0))
        arr(1) = "scala"
        println(arr(1))
    }

    def threeTimesThree(list: List[Int]): List[Int] = list match {
        case head :: tail if head % 3 == 0 =>
            (head * 3) :: threeTimesThree(tail)
        case head :: tail =>
            head :: threeTimesThree(tail)
        case Nil => Nil
    }

    def test5(): Unit = {
        val list1 = List("hello", "world")
        println(s"list1 => $list1")
        val list2 = 1 :: 2 :: 3 :: Nil // Nil.::(1).::(2).::(3)
        println(s"list2 => $list2")
        val list3 = 0 :: list2
        println(s"list3 => $list3")
        val list4 = threeTimesThree(list3)
        println(s"list4 => $list4")
    }

    def test6(): Unit = {
        val set1 = Set(3, 2, 1, 3)
        println(s"set1 => $set1")
        val set2 = List(3, 2, 1, 3).toSet
        println(s"set2 => $set2")
    }

    def test7(): Unit = {
        val map1 = Map((1, "a"), (2, "b"))
        println(s"map1 => $map1")
        val map2 = Map(1 -> "a", 2 -> "b")
        println(s"map2 => $map2")
        val map3 = List(1 -> "a", 2 -> "b").toMap
        println(s"map3 => $map3")
        println(s"map3.get(1) =>${map3.get(1)}")
        println(s"map3.get(10) =>${map3.get(10)}")
    }

    def test8(): Unit = {
        val list1 = List("a", "b", "c")
        list1.zipWithIndex.foreach(pair => println(s"pair => ${pair._1}, ${pair._2}"))
        val list2 = list1.filter(c => c != "b")
        println(s"list2 => $list2")
        val count = list1.count(c => c != "b")
        println(s"count => $count")
        val contains = list1.contains("b")
        println(s"contains => $contains")
        val list3 = list1 ++ list2
        println(s"list3 => $list3")
        val list4: List[Any] = list1 ++ List(1, 2, 3)
        println(s"list4 => $list4")
        val mkString = list4.mkString("[[", ",, ", "]]")
        println(s"mkString => $mkString")
    }
}
