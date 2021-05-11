object Palindrome {

  def check(string : String): Unit ={

    val string2 = string.reverse
    if(string.equals(string2)){
      println("It's a Palindrome")
    } else println("It is NOT a palindrome")
  }

  def pal(): Unit ={
    println("Check Palindrome")
    val string = scala.io.StdIn.readLine("Enter a string: ")
    check(string)
  }

  def main(args: Array[String]): Unit = {
    pal()
  }

}
