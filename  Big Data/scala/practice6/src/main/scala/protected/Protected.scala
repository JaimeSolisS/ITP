package `protected`

object Protected {

  class Vehicle {
    protected def turnOnMotor() {
      println("Motor turned on")
    }
  }

  class Car extends Vehicle {
    turnOnMotor()
  }

  class Bicycle {
    (new Vehicle).myFunc() // not accessible
  }

}


