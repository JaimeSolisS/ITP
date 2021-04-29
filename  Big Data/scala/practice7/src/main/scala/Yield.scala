import Controller.DataGenerator
import Model.Person

object Yield {

  def example(): Unit ={
    var a = 0;
    val numList = List(1,2,3,4,5,6,7,8,9,10);
    // for loop execution with a yield
    var retVal = for{ a <- numList if a != 3; if a < 8 }yield a
    // Now print returned values using another loop.
    for( a <- retVal) {
      println("Value of a: " + a);
    }
  }

  def getAdults(peopleList : List[Person]) : List[Person] ={
    var adults = for {person <- peopleList if person.age > 18} yield person
    return adults
  }

  //Filter people with names that start with A
  def getAnames(peopleList : List[Person]): List[Person] ={
    var names = for {person <- peopleList if person.name.startsWith("A")} yield person
    return names
  }

  def printList(list : List[Person]): Unit ={
    for (person <- list){
      println("Name: " + person.name + " Age: " + person.age)
    }
  }

  def main(args: Array[String]): Unit = {

    val peopleList: List[Person] = DataGenerator.generateData(30)
    val adults = getAdults(peopleList)
    val namesWithA =getAnames(peopleList)

    printList(adults)
    println("------")
    printList(namesWithA)


  }

}
