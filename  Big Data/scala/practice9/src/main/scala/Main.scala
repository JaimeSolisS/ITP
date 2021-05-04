import Model.Person

import java.time.Instant
import java.time.Duration
import scala.collection.mutable.ListBuffer
import scala.util.Random

object Main {

  def createList (amount : Int): List[Person] = {
    var start = Instant.now
    val newList: List[Person] = List.createList(amount)
    var finish = Instant.now
    var timeElapsed = Duration.between(start, finish).toMillis
    return newList
  }

  def getRandom (amount: Int, list : List[Person]): List[Person] ={
    val randomList = new ListBuffer[Person]()
    val random = new Random
    for (a <- 1 to amount){
      randomList += list(random.nextInt(list.length))
    }
    val listR = randomList.toList
    return listR
  }

  def main(args: Array[String]): Unit = {

    val list1k =  createList(1000)
    val list10k =createList(10000)
    val list100k =createList(100000)
    val random1k = getRandom(10, list1k)
    val random10k = getRandom(10, list10k)
    val random100k = getRandom(10, list100k)

    println("------REMOVE------")
    println("------List--------")
    List.delete(list1k, random1k)
    List.delete(list10k, random10k)
    List.delete(list100k, random100k)
    println("------Set--------")
    Set.delete(list1k, random1k)
    Set.delete(list10k, random10k)
    Set.delete(list100k, random100k)
    println("------Map--------")
    Map.delete(list1k, random1k)
    Map.delete(list10k, random10k)
    Map.delete(list100k, random100k)

    println("------UPDATE------")
    println("------List--------")
    List.update(list1k, random1k)
    List.update(list10k, random10k)
    List.update(list100k, random100k)
    println("-----Set--------")
    Set.update(list1k, random1k)
    Set.update(list10k, random10k)
    Set.update(list100k, random100k)
    println("------Map--------")
    Map.update(list1k, random1k)
   Map.update(list10k, random10k)
    Map.update(list100k, random100k)
  }


}
