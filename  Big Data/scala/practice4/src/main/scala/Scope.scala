object Scope {
  class User (nameP: String, ageP:Int) {
    var name: String = nameP
    var age: Int = ageP

    def whatIsYourAge(): Unit = {
      println(this.age)
    }
  }
  def objectParam(user: User) : Boolean = {
    user.age = 32
    return true
  }
  def objectField(age:  Int) : Boolean = {
    age = 32
    return true
  }

  def main(args: Array[String]) {
    val myUser = new User("Jaime", 24)


    objectParam(myUser)
    objectField()
    myUser.whatIsYourAge()


  }

}
