import Model.Person

import java.time.{Duration, Instant}

object Set {

  def delete(list:List[Person], elementsToFind:List[Person]): Unit ={
    var start = Instant.now
    //create mutable set
    val set = list.to(collection.mutable.Set)

    //delete elements
    for (element <- elementsToFind){
      set -= element
    }
    var finish = Instant.now
    var timeElapsed = Duration.between(start, finish).toMillis
    println("delete with " + list.length + " items: "+ timeElapsed +" ms")
  }

  def update(list:List[Person], elementsToFind:List[Person]): Unit ={
    var start = Instant.now
    //create set
    val set = list.to(collection.mutable.Set)
    //delete element and insert an "updated" one
    for (element <- elementsToFind){
      val name = element.name + "-update"
      val age = element.age
      set -= element
      val person = new Person(name, age)
      set += person
      //set.find(_==item)
      //set(item.name)= item.name + "Updated"
    }
    var finish = Instant.now
    var timeElapsed = Duration.between(start, finish).toMillis
    println("update with " + list.length + " items: "+ timeElapsed +" ms")


/*
    for (element <- elementsToFind){
      println(set.find(_==element))
    }
    */



  }
}
