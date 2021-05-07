object Main {

  trait Aux  {
    def hello
  }

  trait A extends Aux {
    override def hello {
      println("hello hello method from A")
      //super.hello
    }
  }

  trait B extends Aux  {
    override def hello {
      println("hello method from B")
      //super.hello
    }
  }

  trait C extends Aux {
    override def hello {
      println("hello method from C")
      //super.hello
    }
  }

  def main(args: Array[String]): Unit = {

    class D extends A with B with C{
     def helloA = super[A].hello
      def helloB = super[B].hello
      def helloC = super[C].hello
    }

    val d = new D

    //d.helloA
    d.helloB
    //d.helloC


  }
}
