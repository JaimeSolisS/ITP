object Declarations {

  abstract class Math {
    //abstract declarations
    def multiply(a:Int ,b:Int): Int
    def hello()
  }

  class Calculator extends Math{
    //implementation of abstract method
    def multiply(a: Int, b: Int): Int = a*b  //one line function
    //implementation of abstract method
    def hello(){
      println("Hello")
    }
    //Concrete declaration
    def sum(a: Int, b:Int): Unit = {
      println(a+b)
    }
  }

  def main(args: Array[String]): Unit = {
    var calc = new Calculator
    calc.hello()
    println(calc.multiply(4,3))
    calc.sum(4,3)
  }



}
