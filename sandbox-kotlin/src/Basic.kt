private fun basic_number_1(): Unit {
    val a: Int = 10000
    println(a === a)
    val boxedA: Int? = a
    val anotherBoxedA: Int? = a
    println(boxedA === anotherBoxedA)
    println(boxedA == anotherBoxedA)
}

private fun basic_number_2(): Unit {
    val a: Int? = 1
    //val b: Long? = a
    val b: Long? = a?.toLong()
    //println(a == b)

    val byte: Byte = 1
    val int: Int = byte.toInt()

    val l = 1L + 3
    println(l.javaClass)
}

private fun basic_number_3(): Unit {
    val x = (1 shl 2) and 0x000FF000
}

private fun basic_char(): Unit {
    fun check(c: Char) {
        //if (c == 1) {
        //}
    }

    fun decimalDigitValue(c: Char): Int {
        if (c !in '0'..'9') throw IllegalArgumentException("Out of range")
        return c.toInt() - '0'.toInt()
    }
}

private fun basic_array(): Unit {
    val list = arrayOf(1, 2, 3)
    val arr: Array<Int?> = arrayOfNulls(3)
    val asc = Array(5, { it -> (it * it).toString() })

    println(list)
    println(arr)
    println(asc)

    val x: IntArray = intArrayOf(1, 2, 3)
    x[0] = x[1] + x[2]
    println(x)
}

private fun basic_string(): Unit {
    for (c in "abcde") println(c)

    val s = "Hello, world!\n"
    val text = """
for (c in "foo")
    print(c)
"""

    println(s)
    println(text)

    val int = 10
    val sint = "int = $int"
    val str = "abc"
    val sstr = "$str.length is ${str.length}"
    val price = "${'$'}9.99"
    val price2 = """$9.99"""

    println(sint)
    println(sstr)
    println(price)
    println(price2)
}

private fun basic_statement(): Unit {
    val a: Int = 3
    val b: Int = 4
    val max = if (a > b) a else b
    println(max)

    when (max) {
        3 -> println("max == 3")
        4 -> println("max == 4")
        else -> println("max is neighter 3 nor 4")
    }

    when (max) {
        3, 4 -> println("max == 3 or max == 4")
        else -> println("otherwise")
    }

    when (max) {
        Integer.parseInt("4") -> println("s encodes max")
        else -> println("s does not encde max")
    }

    when (max) {
        in 1..10 -> println("max is in the range")
        in arrayOf(100, 1000, 10000) -> println("max is valid")
        !in 10..20 -> println("max is outside the range")
        else -> println("none of the abobe")
    }

    val x = "prefix___"
    val hasPrefix = when (x) {
        is String -> x.startsWith("prefix")
        else -> false
    }
    println(hasPrefix)

    val arr: Array<Int> = arrayOf(1, 3, 5, 7, 9)
    for (item in arr) {
        println(item)
    }
    for (i in arr.indices) {
        println(arr[i])
    }
    for ((index, value) in arr.withIndex()) {
        println("the element at $index is $value")
    }

    var cnt = 10
    while (cnt > 0) {
        cnt--
    }

    fun retrieveData(): String? {
        return if (cnt > 3) {
            null
        } else {
            cnt++
            "not null"
        }
    }
    do {
        val y = retrieveData()
        println(y)
    } while (y != null)
}

data class X(val v: Int) : Comparable<X> {
    override fun compareTo(other: X): Int {
        return this.v - other.v
    }

    operator fun rangeTo(other: X): XRange {
        return XRange(this, other)
    }
}

class XRange(override val start: X, override val endInclusive: X) : ClosedRange<X>, Iterable<X> {
    override fun iterator(): Iterator<X> {
        return object : Iterator<X> {
            var current: X = start

            override fun next(): X {
                val next = X(current.v)
                current = X(current.v + 1)
                return next
            }

            override fun hasNext(): Boolean {
                return (current.compareTo(endInclusive)) <= 0
            }
        }
    }
}

private fun basic_operator_overload(): Unit {
    data class BBB(val x: Int, val y: Int)

    data class AAA(val x: Int, val y: Int) {
        operator fun plus(other: AAA): AAA {
            return AAA(x + other.x, y + other.y)
        }

        operator fun div(unit: Int): BBB {
            return BBB(x / unit, y / unit)
        }
    }

    val a1 = AAA(2, 3)
    val a2 = AAA(4, 9)
    val a3 = a1 + a2
    println(a3)
    val b = a3 / 3
    println(b)

    for (x in X(10)..X(14)) {
        println(x)
    }
}

private fun basic_jvm(): Unit {
    fun `is`(x: Int, y: Int): Boolean {
        return x == y
    }

    val x = 3
    val y = 3
    println(`is`(x, y))
}


fun basic_run(): Unit {
    println("---- Basic ----")

    basic_number_1()
    basic_number_2()
    basic_number_3()
    basic_char()
    basic_array()
    basic_string()
    basic_statement()
    basic_operator_overload()
    basic_jvm()
}
