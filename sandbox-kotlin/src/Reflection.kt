import kotlin.reflect.KAnnotatedElement

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

fun reflection_run(): Unit {
    println("---- Reflection ----")

    reflection_annotation()
}
