fun main(args: Array<String>) {
    println("hello, world")

    println(map(3, ::next))
    println(map(3, succ))
    println(map(3, plus))
    println(map(4, { it -> it + 3 }))
    println(map(5, { i: Int -> i + 5 }))
}

fun next(i: Int): Int = i + 1
val succ = { i: Int -> i + 1 }
val plus: (Int) -> Int = { i -> i + 1 }
