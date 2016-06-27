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
}


fun generics_run(): Unit {
    println("---- Generics ----")

    generics_data_class()
    generics_type_parameter()
}
