package scope_protection.vehicle.car

class Car {
  private[car] var carDetails = null
  private[vehicle] var driver = null
  private[this] var serialNum = null

  def help(another : Car) {
    println(another.driver)
    println(another.serialNum) //Error
  }

}
