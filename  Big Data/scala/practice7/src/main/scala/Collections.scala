import Model.Person
import Controller.DataGenerator

object Collections {

  def example(): Unit ={
    var a = 0;
    val numList = List(1, 2, 3, 4, 5, 6);
    // for loop execution with a collection
    for (a <- numList) {
      println("Value of a: " + a);
    }
  }

  def example2(peopleList : List[Person]){
    for (person <- peopleList) {
      println("Name: " + person.name + " Age: " + person.age );
    }
  }

  def example3(peopleList : List[Person]){
    for (person <- peopleList.reverse) {
      println("Name: " + person.name + " Age: " + person.age );
    }
  }

  def main(args: Array[String]): Unit = {
    val peopleList: List[Person] = DataGenerator.generateData(10)
    //example()
    example2(peopleList)
    println("------Reverse-------")
    example3(peopleList)


  }
}
