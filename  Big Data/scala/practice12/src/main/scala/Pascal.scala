object Pascal {
  //Write shell script for generating pascal triangle


  def generateTriangle (levels : Int): Unit ={
    for (i <- 1 to levels){
      for (j <- 0 to levels-i)
        print(" ")
      var coef = 1
      for (j <- 1 to i ){
        print(" " + coef)
        coef = coef * (i - j) / j
      }
      println("")
    }

  }

  def pascal(): Unit ={
    println("Pascal Triangle ")
    println("Levels ")
    val levels =scala.io.StdIn.readInt()
    generateTriangle(levels)

  }

  def main(args: Array[String]): Unit = {
    pascal()
  }
}
