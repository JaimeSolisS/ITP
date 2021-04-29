import Controller.DataGenerator
import Model.Person

object Filter {

  def example(): Unit ={
    var a = 0;
    val numList = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    // for loop execution with multiple filters
    for (a <- numList
         if a != 3; if a < 8) {
      println("Value of a: " + a);
    }
  }

  def example2(peopleList : List[Person]): Unit ={
    for (person <- peopleList if person.age < 18){
      println("Name: " + person.name + " Age: " + person.age)
    }

  }

  //Filter people with names that start with A
  def example3(peopleList : List[Person]): Unit ={
    for (person <- peopleList if person.name.startsWith("A")){
      println("Name: " + person.name + " Age: " + person.age)
    }
  }

  def main(args: Array[String]): Unit = {
    val peopleList: List[Person] = DataGenerator.generateData(30)
    //example()
    //example2(peopleList)
    example3(peopleList)
  }
}
