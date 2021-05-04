import Data.DataGenerator
import Model.Person

import java.time.{Duration, Instant}
import scala.collection.mutable.ListBuffer

object List {

  def createList(amount : Int): List[Person] = {
    var newList = DataGenerator.generateData(amount)
    return newList
  }

  def delete(list:List[Person], elementsToFind:List[Person]): Unit = {
    var start = Instant.now
    //create mutable list
    var mList = ListBuffer[Person]()
    for (element <- list) {
      mList += element
    }

    //delete elements
    for (element <- elementsToFind) {
      mList -= element
    }
    var finish = Instant.now
    var timeElapsed = Duration.between(start, finish).toMillis
    println("delete with " + list.length + " items: "+ timeElapsed +" ms")
  }

  def update(list:List[Person], elementsToFind:List[Person]): Unit = {
    var start = Instant.now
    //create mutable list
    var mList = new ListBuffer[Person]()
    for (element <- list) {
      mList += element
    }

    //update elements
    for (element <- elementsToFind) {
      for (person <- mList
           if person.name == element.name && person.age == element.age) {
        person.name = person.name + " -Updated"
      }
    }
    var finish = Instant.now
    var timeElapsed = Duration.between(start, finish).toMillis
    println("update with " + list.length + " items: "+ timeElapsed +" ms")

  }

}
