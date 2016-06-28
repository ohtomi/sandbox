private fun generics_data_class(): Unit {
    data class Age(var value: Int)
    data class User(val name: String, val age: Age)

    val user = User("kenichi ohtomi", Age(5))
    println(user)
    val copy = user.copy()
    copy.age.value++
    println(copy)
    println(user)

    val (name, age) = user
    println("$name, ${age.value} years of age")
}

private fun generics_type_parameter(): Unit {
    class Box<T>(t: T) {
        var value: T = t
    }

    val box1: Box<Int> = Box<Int>(1)
    val box2 = Box(1)

    abstract class Source<out T> {
        abstract fun nextT(): T
    }

    fun demo1(strs: Source<String>) {
        val objects: Source<Any> = strs
    }

    abstract class Comparable<in T> {
        abstract fun compareTo(other: T): Int
    }

    fun demo2(x: Comparable<Number>) {
        x.compareTo(1.0)
        val y: Comparable<Double> = x
    }

    class Array<T>(val size: Int) {
        val items: MutableList<T> = mutableListOf()

        fun get(index: Int): T {
            return items.get(index)
        }

        fun set(index: Int, value: T) {
            items.add(value)
        }
    }

    fun copy(from: Array<out Number>, to: Array<in Number>) {
        assert(from.size == to.size)
        var cnt = 0
        while (cnt < from.size) {
            to.set(cnt, from.get(cnt))
            cnt++
        }
    }

    val ints: Array<Int> = Array<Int>(3)
    for (it in 0..2) ints.set(it, it + 100)
    val anys = Array<Any>(3)
    copy(ints, anys)

    val nums: Array<*> = ints
    val a: Any? = nums.get(1)
    //val n: Number = nums.get(0)
    //val i: Int? = nums.get(0)
    println(a)

    fun <T> singletonList(item: T): List<T> = listOf(item)
    val l = singletonList <Int>(3)
    println(l)
}


fun generics_run(): Unit {
    println("---- Generics ----")

    generics_data_class()
    generics_type_parameter()
}
