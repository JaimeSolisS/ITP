object Constructors {

  //Primary - parameter
  class Primary(var name: String = "Jaime", var age: Int) {
    println(s"$name is $age years old")
  }

  class Auxiliary(var name: String, var age: Int ) {
   //Just name passed
    def this(name: String){
      this(name, 24)
      this.name=name
    }

    //Just Age passed
    def this(age: Int){
      this("Jaime", age)
      this.age =age
    }

    //None passed
    def this(){
      this("Jaime", 24)
    }
    println(s"$name is $age years old")

  }
  def main(args: Array[String]) {

    val primary1 = new Primary("Jorge", age = 24)
    val primary2 = new Primary(age = 24)
    val auxiliary1 = new Auxiliary("Jaime", 24)
    val auxiliary2 = new Auxiliary("Jaime")
    val auxiliary3 = new Auxiliary(24)
    val auxiliary4 = new Auxiliary()
  }
}
