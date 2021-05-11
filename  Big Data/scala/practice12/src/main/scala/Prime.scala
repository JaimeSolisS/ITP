import scala.util.control.Breaks.break

object Prime {
  // Write shell script for generating prime numbers in a range

  def generateSequence (low: Int, high: Int): Unit = {
    print("Sequence: ")
    for (num <- low to high){
      var prime = true
      for (i <- 2 until num) {
        if ((num % i) == 0)
          prime = false
      }
      if (prime) print(num + ", ")
    }
  }

  def prime (): Unit ={
    println("Prime Numbers:")
    println("Lower number ")
    val low =scala.io.StdIn.readInt()
    println("higher number ")
    val high =scala.io.StdIn.readInt()
    generateSequence(low, high)
  }

  def main(args: Array[String]): Unit = {
    prime()
  }


}
