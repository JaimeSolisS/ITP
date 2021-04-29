package Controller

import Model.Person

import scala.collection.mutable.ListBuffer
import scala.util.Random

object DataGenerator {

  val nameList: List[String] = List("Michael", "Christopher", "Jessica", "Matthew", "Ashley", "Jennifer", "Joshua", "Amanda", "Daniel", "David", "James", "Robert", "John", "Joseph", "Andrew", "Ryan", "Brandon", "Jason", "Justin", "Sarah", "William", "Jonathan", "Stephanie", "Brian", "Nicole", "Nicholas", "Anthony", "Heather", "Eric", "Elizabeth", "Adam", "Megan", "Melissa", "Kevin", "Steven", "Thomas")
  var listPerson = new ListBuffer[Person]()
  val start = 10
  val end   = 90

  def generateData(elements: Int): List[Person] = {
    for (i <- 1 to elements ){
      val rnd = Random.between(10,30) //random age
      val rndName = new Random
      val name = nameList(rndName.nextInt(nameList.length)) //random name
      val person = new Person(name, rnd);
      listPerson += person
    }
    val list = listPerson.toList

   /* for(element<-listPerson){
      println(element.name, element.age)
    }*/

    return list

  }

}
