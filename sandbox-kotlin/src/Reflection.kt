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

private interface Renderable {
    fun render(builder: StringBuilder)
}

private data class MyHTML(val text: String, val children: MutableList<Renderable> = mutableListOf()) : Renderable {
    fun head(init: MyHEAD.() -> Unit): MyHEAD {
        val head = MyHEAD("head")
        head.init()
        children.add(head)
        return head
    }

    fun body(init: MyBODY.() -> Unit): MyBODY {
        val body = MyBODY()
        body.init()
        children.add(body)
        return body
    }

    override fun render(builder: StringBuilder) {
        builder.append("{html: [")
        children.map { it.render(builder) }
        builder.append("]}")
    }
}

private data class MyHEAD(val text: String) : Renderable {
    override fun render(builder: StringBuilder) {
        builder.append("{head: '$text'}, ")
    }
}

private data class MyBODY(val children: MutableList<Renderable> = mutableListOf()) : Renderable {
    fun div(init: MyDIV.() -> Unit): MyDIV {
        val div = MyDIV()
        div.init()
        children.add(div)
        return div
    }

    override fun render(builder: StringBuilder) {
        builder.append("{body:[")
        children.map { it.render(builder) }
        builder.append("]}")
    }
}

private data class MyDIV(val children: MutableList<Renderable> = mutableListOf()) : Renderable {
    operator fun String.unaryPlus(): MyTextNode {
        val node = MyTextNode(this)
        children.add(node)
        return node
    }

    override fun render(builder: StringBuilder) {
        builder.append("{div:")
        children.map { it.render(builder) }
        builder.append("}, ")
    }
}

private data class MyTextNode(val text: String) : Renderable {
    override fun render(builder: StringBuilder) {
        builder.append("'$text'")
    }
}

private fun reflection_builder(): Unit {
    fun html(init: MyHTML.() -> Unit): MyHTML {
        val html = MyHTML("html")
        html.init()
        return html
    }

    val doc = html {
        head { }
        body {
            div { +"---- div 1 ----" }
            div { +"---- div 2 ----" }
            div { +"---- div 3 ----" }
        }
    }
    println(doc)
    println(StringBuilder().apply { doc.render(this) }.toString())
}

fun reflection_run(): Unit {
    println("---- Reflection ----")

    reflection_annotation()
    reflection_reference()
    reflection_builder()
}
