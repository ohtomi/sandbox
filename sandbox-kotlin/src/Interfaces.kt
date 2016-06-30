private interface MyInterface {
    val property: Int
    val propertyWithImplementation: String
        get() = "foo"

    fun bar()
    fun foo() {
        println(property)
    }
}

private fun interfaces_property(): Unit {
    class Child : MyInterface {
        override val property: Int = 29
        override fun bar(): Unit {
            println(propertyWithImplementation)
        }
    }

    val c = Child()
    c.foo()
    c.bar()
}

private open class Outer {
    private val a = 1
    protected val b = 2
    internal val c = 3
    val d = 4

    protected class Nested {
        public val e = 5

        fun method() {
            val outer = Outer()
            println(outer.a + outer.b + outer.c + outer.d)
        }
    }

    inner class Inner {
        fun method() {
            println(a + b + c + d)
        }
    }
}

private fun interfaces_scope(): Unit {
    class Subclass : Outer() {
        fun method() {
            val outer = Outer()
            val nested = Outer.Nested()
            println(outer.b + outer.c + outer.d)
            println(nested.e)
        }
    }

    class Unrelated(o: Outer) {
        fun method() {
            val outer = Outer()
            //val nested = Outer.Nested()
            println(outer.c + outer.d)
        }
    }

    val inner = Outer().Inner()
    inner.method()
}

private val <T> List<T>.lastIndex: Int
    get() = size - 1

private class MyClass {
    companion object {
        val repr = "companion"
    }

    val name = "kenichi"
}

private fun interface_extend(): Unit {
    fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
        val tmp = this[index1]
        this[index1] = this[index2]
        this[index2] = tmp
    }

    val list = mutableListOf(1, 3, 5)
    list.swap(0, 2)
    println(list)

    open class C {
        fun bar() = "c#bar"
    }

    class D : C()

    fun C.foo() = "c"
    fun D.foo() = "d"

    fun C.bar() = "c"
    fun D.bar() = "d"

    fun printD(c: C) {
        println(c.foo())
        if (c is D) {
            val d = c as D
            println(d.foo())
        }

        println(c.bar())
        if (c is D) {
            val d = c as D
            println(d.bar())
        }
    }

    printD(D())

    fun Any?.toString(): String {
        if (this == null) return "!NULL!"
        return toString()
    }

    println(null.toString())

    val l = listOf(1, 2, 3, 4, 5)
    println(l.lastIndex)

    fun MyClass.Companion.foo() {
        println(this.repr)
    }

    MyClass.foo()
    MyClass.Companion.foo()

    class MySet<E>(private val s: MutableSet<E>) : MutableSet<E> by s {
        fun xxx() {
            println("size: $size")
        }
    }

    val s = MySet(mutableSetOf(1, 3, 5))
    s.xxx()
    s.add(77)
    s.xxx()
}


fun interfaces_run(): Unit {
    println("---- Interfaces ----")

    interfaces_property()
    interfaces_scope()
    interface_extend()
}
