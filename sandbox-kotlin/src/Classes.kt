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

}


fun classes_run(): Unit {
    println("---- Classes ----")

    classes_class_1()
    classes_class_2()
    classes_inherit()
}