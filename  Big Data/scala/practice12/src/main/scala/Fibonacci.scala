object Fibonacci {

  //Write shell script for generating fibonacci sequence
  def generateSecuence( numElements : Int )  {
    var x = 0
    var y = 1
    if (numElements < 1){
      println("Choose a positive value")
    } else if (numElements == 1){
      println(x)
    } else if (numElements ==2){
      println(x + ", " + y)
    }else {
      print("Secuence: " + x + ", " + y)
      for (i <- 1 to numElements-2 ){
        var num = x +y
        print(", " +num)
        x = y
        y = num
      }
    }


  }

  def fib(): Unit = {
    println("Fibonacci")
    println("How many Elements ")
    val numElements =scala.io.StdIn.readInt()
    generateSecuence(numElements)

  }

  def main(args: Array[String]): Unit = {
    fib()
  }

}
