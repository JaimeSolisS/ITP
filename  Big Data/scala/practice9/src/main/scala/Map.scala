import Model.Person

import java.time.{Duration, Instant}
import scala.collection.mutable.ListBuffer

object Map {
  def delete(list:List[Person], elementsToFind:List[Person]): Unit ={
    var start = Instant.now
    //make key list key=name+age
    var keys = new ListBuffer[String]()
    for(person <- list){
      keys += (person.name + person.age)
    }
    //make keys to delete
    var keysToDelete = new ListBuffer[String]()
    for(person <- elementsToFind){
      keysToDelete += (person.name + person.age)
    }
    //convert list to map
    val map = (keys zip list).to(scala.collection.mutable.Map) //with keys
    //val map = list.zipWithIndex.map{ case (v,i) => (i,v) }.toMap with index

    //delete elements
    for (key <- keysToDelete){
      map.remove(key)
    }
    var finish = Instant.now
    var timeElapsed = Duration.between(start, finish).toMillis
    println("delete with " + list.length + " items: "+ timeElapsed +" ms")
  }

  def update(list:List[Person], elementsToFind:List[Person]): Unit ={
    var start = Instant.now
    //make key list key=name+age
    var keys = new ListBuffer[String]()
    for(person <- list){
      keys += (person.name + person.age)
    }
    //make keys to delete
    var keysToDelete = new ListBuffer[String]()
    for(person <- elementsToFind){
      keysToDelete += (person.name + person.age)
    }
    //convert list to map
    val map = (keys zip list).to(scala.collection.mutable.Map) //with keys
    //val map = list.zipWithIndex.map{ case (v,i) => (i,v) }.toMap with index

    //update elements
    for (key <- keysToDelete){
      map(key).name = map(key).name + "-UPDATE"
    }
    /*
    for (key <- keysToDelete){
      println(map(key).name)
    }*/

    var finish = Instant.now
    var timeElapsed = Duration.between(start, finish).toMillis
    println("update with " + list.length + " items: "+ timeElapsed +" ms")
  }


}
