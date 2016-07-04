import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantReadWriteLock

private fun functions_infix(): Unit {
    class A() {
        infix fun foo(x: Int) {
            println("foo $x")
        }

        operator fun div(x: Int) {
            println("div $x")
        }
    }

    val a = A()
    a foo 3
    a / 3
}

private fun functions_parameters(): Unit {
    fun foo(a: Int, b: Int = 1, c: Int = 2) {
        println("$a, $b, $c")
    }

    foo(10)
    foo(10, 20)
    foo(10, 20, 30)

    foo(10, c = 20)
    foo(b = 10, a = 20)

    fun x(a: Int = 2, b: Int) {
        println("$a, $b")
    }

    x(b = 3)
}

private fun functions_vargs(): Unit {
    fun <T> foo(vararg ts: T): Array<out T> {
        val arr = ts
        return arr
    }

    val arr = foo(1, 3, 5, 7, 1.0)
    println("arr is ${arr.javaClass} (${arr.javaClass.kotlin})")
    for (a in arr) {
        println(a)
    }
}

private fun functions_tailrec(): Unit {
    tailrec fun findFixPoint(x: Double = 1.0): Double
            = if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))

    println(findFixPoint())
}

private fun functions_higher_order(): Unit {
    fun lock(lock: Lock, body: () -> Unit): Unit {
        lock.lock()
        try {
            body()
        } finally {
            lock.unlock()
        }
    }

    lock(ReentrantReadWriteLock().readLock()) {
        println("body")
    }

    arrayOf("abc", "def", "ghijk", "lmn").filter { it.length == 3 }.sortedBy { it }.map { it.toUpperCase() }.forEach {
        println(it)
    }
}

private fun functions_literal(): Unit {
    fun <T> max(collection: Array<T>, less: (T, T) -> Boolean): T? {
        var max: T? = null
        for (it in collection) {
            max = if (max == null || less(max, it)) it else max
        }
        return max
    }

    val m = max(arrayOf(1, 3, 9, 2, 3)) { a, b -> a < b }
    println("max $m")

    var sum = 0
    val ints = arrayOf(-2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    ints.filter { it > 0 }.forEach { sum += it }
    println(sum)

    val intSum = fun Int.(other: Int): Int = this + other
    println(1.intSum(3))

    class HTML {
        var name: String? = null

        fun body(name: String) {
            this.name = name
        }

        override fun toString(): String {
            return "HTML($name)"
        }
    }

    fun html(init: HTML.() -> Unit): HTML {
        val html = HTML()
        html.init()
        return html
    }

    val dom = html {
        body("my html")
    }
    println(dom)
}

private inline fun foo(fn: () -> Unit) {
    //val x = fn
    fn()
}

private inline fun bar(noinline fn: () -> Unit) {
    val x = fn
    x()
}

private fun baz(fn: () -> Unit) {
    val x = fn
    x()
}

private inline fun buz(crossinline fn: () -> Unit) {
    //val x = fn
    foo(fn)
    //bar(fn)
    //baz(fn)
}

private fun functions_inline(): Unit {
    foo { println("inline") }
    bar { println("noinline") }
    buz { println("crossinline") }

    baz {
        println("local returning from baz")
        return@baz
    }
    foo {
        println("non-local returning from foo")
        return
    }
    println("unreachable code")
}

private inline fun <reified T> hoge(): T {
    val t = T::class.constructors.first()
    return t.call()
}

private fun functions_reified(): Unit {
    class A

    val a = hoge<A>()
    println(a)
}

private fun functions_component(): Unit {
    data class Result(val result: Int, val status: String)

    fun foo(x: Int, y: Int): Result {
        return Result(x + y, "no error")
    }

    val (result, status) = foo(2, 3)
    println("result: $result, status: $status")

    val m = mapOf("name" to "kenichi", "age" to "9")
    for ((key, value) in m) {
        println("$key: $value")
    }
}

private fun functions_range(): Unit {
    val x = 12
    for (n in 1..x) println("1..x $n")
    for (n in 1.rangeTo(x)) println("1.rangeTo $n")
    for (n in x.downTo(1)) println("x.downTo $n")
    for (n in (1..x).reversed()) println("1..x reversed $n")
    for (n in 1..x step 2) println("1..x step 2 $n")
    for (n in 1..x step 3) println("1..x step 3 $n")
    for (n in 1..x step 4) println("1..x step 4 $n")
    for (n in 1..x step 4) println("1..x step 4 $n")
}


fun functions_run(): Unit {
    println("---- Functions ----")

    functions_infix()
    functions_parameters()
    functions_vargs()
    functions_tailrec()
    functions_higher_order()
    functions_literal()
    functions_inline()
    functions_reified()
    functions_component()
    functions_range()
}
