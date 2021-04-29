object Range {

  def example() {

    for( a <- 1 to 10){
      println( "Value of a: " + a );
    }
    for( a <- 1 to 3; b <- 1 to 3){
      println( "Value of a: " + a );
      println( "Value of b: " + b );
    }
  }

  //from n to n-1
  def exampleUntil() {
    for( x <- 0 until 10) {
      println(x);
    }
  }

  def exampleBackwards() {
    for( x <- 10 until 0 by -1) {
      println(x);
    }
  }

  def exampleBackwards2() {
    for( x <- (10 until 0).reverse ) {
      println(x);
    }
  }

  def exampleBackwards3() {
    for( x <- 10 until (0,-1)) {
      println(x);
    }
  }

  def main(args: Array[String]): Unit = {
    //example()
    //exampleUntil()
    //exampleBackwards()
    //exampleBackwards2() //does not work
    //exampleBackwards3()

  }

}
