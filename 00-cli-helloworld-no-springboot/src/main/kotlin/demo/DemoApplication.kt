package demo

class DemoApplication {
    fun run() {
        println(getHello())
    }
    fun getHello(): String = "HelloWorld"
}

fun main() {
    DemoApplication().run()
}
