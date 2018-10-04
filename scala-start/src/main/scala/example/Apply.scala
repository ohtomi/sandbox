package example

object Apply {

    val generator = new scala.util.Random

    case class CaseClass(i: Int) {

        val randomValue = Apply.generator.nextInt
    }

    class ApplyAndToString(val s: String) {

        def apply(): String = s"s.length is ${s.length}"
        override def toString(): String = s"ApplyAndToString has ${s}"
    }

    def test0(): Unit = {
        val cc1 = CaseClass(10) // CaseClass.apply(10)
        println(s"cc1 => ${cc1}")     // cc1.toString()

        val aa1 = new ApplyAndToString("123")
        println(s"aa1 => ${aa1}")     // aa1.toString()
        println(s"aa1() => ${aa1()}") // aa1.apply()
    }

    def test1(): Unit = {
        val cc1 = CaseClass(10)
        println(s"cc1.randomValue => ${cc1.randomValue}")

        val cc2 = cc1.copy()
        println(s"cc2.randomValue => ${cc2.randomValue}")
    }

    def checkCaseClass(cc: CaseClass): Unit = cc match {
        case CaseClass(1) =>
            println(s"i of cc is one.")
        case CaseClass(10) =>
            println(s"i of cc is ten.")
        case _ =>
            println(s"i of cc is ${cc.i}")
    }

    def test2(): Unit = {
        val cc1 = CaseClass(1)
        Apply.checkCaseClass(cc1)
        val cc2 = CaseClass(10)
        Apply.checkCaseClass(cc2)
        val cc3 = CaseClass(100)
        Apply.checkCaseClass(cc3)
    }
}
