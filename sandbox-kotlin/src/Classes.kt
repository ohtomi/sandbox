import java.util.*
import kotlin.reflect.jvm.internal.impl.javax.inject.Inject

private fun classes_class_1(): Unit {
    class Empty

    class Customer(name: String) {
        init {
            println("Customer initialized with value ${name}")
        }

        val customerKey = name.toUpperCase()

        constructor(firstName: String, lastName: String) : this("$firstName $lastName") {
            println("firstName = $firstName, lastName = $lastName")
        }
    }

    class Person constructor(firstName: String) {
        val children = ArrayList<Any>()
    }

    class Studen public @Inject constructor(name: String) {
    }

    class Person2 {
        constructor(parent: Person) {
            parent.children.add(this)
        }
    }

    class DontCreateMe private constructor() {
    }

    val empty = Empty()
    val customer1 = Customer("kenichi ohtomi")
    val customer2 = Customer("kenichi", "ohtomi")
    val person1 = Person("kenichi ohtomi")
    val perosn2 = Person2(person1)
    //val student = Student("kenichi ohtomi")
    //val dontCreateme = DontCreateMe()

    println(empty)
    println(customer1)
    println(customer2)
    println(person1)
    println(perosn2)
}

private fun classes_class_2(): Unit {
    class Person(val firstName: String, val lastName: String, var age: Int) {
    }

    val person = Person("kenichi", "ohtomi", 5)
    person.age++
    println("name: ${person.lastName} ${person.firstName}, age: ${person.age}")
}

private interface B {
    fun f() {
        println("B")
    }

    fun b() {
        println("b")
    }
}

private fun classes_inherit(): Unit {
    open class Base(p: Int)
    class Derived(p: Int) : Base(p)

    val d = Derived(123)
    println(d)

    class Context
    class AttributeSet
    open class View(ctx: Context)
    class MyView : View {
        constructor(ctx: Context) : super(ctx) {
        }

        constructor(ctx: Context, attrs: AttributeSet) : super(ctx) {
        }
    }

    val v = MyView(Context(), AttributeSet())
    println(v)

    open class Base2 {
        open fun v() {
        }

        fun nv() {
        }
    }

    class Derived2() : Base2() {
        override fun v() {
        }
    }

    open class AnotherDerived() : Base2() {
        final override fun v() {
        }
    }

    open class A {
        open fun f() {
            println("A")
        }

        fun a() {
            println("a")
        }
    }

    class C() : A(), B {
        override fun f() {
            super<A>.f()
            super<B>.f()
        }
    }

    C().f()
}

fun classes_abstract(): Unit {
    open class Base {
        open fun f() {
        }
    }

    abstract class Derived : Base() {
        override abstract fun f()
    }

}

sealed class Expr {
    class Const(val number: Double) : Expr()
    class Sum(val e1: Expr, val e2: Expr) : Expr()

    object NotANumber : Expr()
}

fun classes_sealed(): Unit {
    fun eval(expr: Expr): Double = when (expr ) {
        is Expr.Const -> expr.number
        is Expr.Sum -> eval(expr.e1) + eval(expr.e2)
        Expr.NotANumber -> Double.NaN
    }

    println(eval(Expr.Sum(Expr.Const(2.0), Expr.Const(3.0))))
}

private const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deperecated"

private object O {
    const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deperecated"
}

fun classes_property(): Unit {
    class A {
        //var allByDefault: Int?
        var initialized = 1
        var stringRepresentation: String
            get() = this.toString()
            set(value) {
                field = "+" ; value + "+"
            }
        var setterVisiblity: String = "abc"
            private set
        var setterWithAnnotation: Any?
            @Inject set

        val simple: Int?
        val inferredType = 1
        val isEmpty: Boolean
            get() = inferredType != 1

        val table: Map<String, Int>? = null
            get() {
                if (field == null)
                    field = HashMap()
                return field
            }

        init {
            stringRepresentation = ""
            simple = 0
            setterWithAnnotation = null
        }
    }

    val table = A().table
    println(table)

    class TestSubject {
        fun method() {
        }
    }

    class MyTest {
        lateinit var subject: TestSubject
        fun setup() {
            subject = TestSubject()
        }

        fun test() {
            subject.method()
        }
    }

    open class Person(open val name: String) {
    }

    class UpperCaseNamePerson(name: String) : Person(name) {
        override val name: String
            get() {
                return super.name.toUpperCase()
            }
    }

    println(Person("kenichi ohtomi").name)
    println(UpperCaseNamePerson("kenichi ohtomi").name)

    class User(val map: Map<String, Any?>) {
        val name: String by map
        val age: Int by map
    }

    val user = User(mapOf("name" to "John Doe", "age" to 25))
    println(user.name)
    println(user.age)
}


fun classes_run(): Unit {
    println("---- Classes ----")

    classes_class_1()
    classes_class_2()
    classes_inherit()
    classes_abstract()
    classes_sealed()
    classes_property()
}
