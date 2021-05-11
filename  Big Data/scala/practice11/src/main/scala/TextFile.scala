import scala.io.Source

object TextFile {

  def readTxt (filename : String): Unit ={

    for (line <- Source.fromFile(filename).getLines) {
      println(line)
    }
    //val fileContents = Source.fromFile(filename).getLines.mkString
    //print(fileContents)
  }

}
