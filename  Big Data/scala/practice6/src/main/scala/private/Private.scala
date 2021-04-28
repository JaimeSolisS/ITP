package `private`

object Private {

    class Vehicle {
      class Car {
        private def closeDoors(): Unit ={
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
