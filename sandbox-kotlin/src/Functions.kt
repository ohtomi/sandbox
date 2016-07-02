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
}


fun functions_run(): Unit {
    println("---- Functions ----")

    functions_infix()
    functions_parameters()
    functions_vargs()
    functions_tailrec()
    functions_higher_order()
    functions_literal()
}
