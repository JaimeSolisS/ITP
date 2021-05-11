object Armstrong {
  // Write shell script for finding armstrong numbers

  def generateSequence (low: Int, high: Int): Unit = {
    print("Sequence: ")
    for(num <- low to high){
     var sum = 0
      for (digit <- num.toString.map(_.asDigit)){
        sum += digit * digit *digit
      }
      if (sum == num){
        print(num + ", ")
      }

    }
  }

  def arm (): Unit ={
    println("Armstrong")
    println("Lower number ")
    val low =scala.io.StdIn.readInt()
    println("higher number ")
    val high =scala.io.StdIn.readInt()
    generateSequence(low, high)
  }

  def main(args: Array[String]): Unit = {
    arm()
  }
}
