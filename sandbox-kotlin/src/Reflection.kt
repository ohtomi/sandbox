private fun reflection_annotation(): Unit {
    @Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION)
    annotation class Fancy

    @Fancy class Foo {
        @Fancy fun baz(@Fancy foo: Int): Int {
            return (@Fancy 1)
        }
    }

    val foo = Foo()
    val bar = object {}
    for (ann in foo.javaClass.kotlin.annotations) {
        println("foo $ann")
    }
    for (ann in bar.javaClass.kotlin.annotations) {
        println("bar $ann")
    }
    println("foo is annotated by Fancy ---> ${foo.javaClass.kotlin.annotations.filter { it.annotationClass == Fancy::class }.isNotEmpty()}")
}

private fun reflection_reference(): Unit {
    val clazz = AnnotationTarget::class
    val jclass = clazz.java
    val kclass = jclass.kotlin

    val strTake = String::take
    //val strToUpperCase = String::toUpperCase
    println(strTake("abcdefg", 3))

    fun f1(p: Pair<Int, Int>): String = "${p.first} + ${p.second} = ${p.first + p.second}"
    fun f2(x: Int): Pair<Int, Int> = Pair(x, x + 100)
    fun f3(f: (Pair<Int, Int>) -> String, g: (Int) -> Pair<Int, Int>): (Int) -> String = { x ->
        f(g(x))
    }

    println(f3(::f1, ::f2)(123))

    data class A(val a: Int, val b: Int)

    data class B(val a: Int, val b: Int) {
        constructor(a: Int) : this(a, 123)
    }

    val ctorA = ::A
    //val ctorB = ::B

    println(ctorA(123, 456))
}

fun reflection_run(): Unit {
    println("---- Reflection ----")

    reflection_annotation()
    reflection_reference()
}
