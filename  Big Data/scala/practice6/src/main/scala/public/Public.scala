package public

object Public {

  class Vehicle {
    class Car {
       def closeDoors(): Unit ={
        println("Hi, I am a car")
      }

      def startUp(): Unit ={
        println("Hi, I am a vehicle")
      }
      class Tesla {
        closeDoors();
      }

    }

    (new Car).closeDoors()
    (new Car).startUp()

  }

}
