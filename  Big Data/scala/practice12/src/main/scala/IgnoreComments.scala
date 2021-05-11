import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object IgnoreComments {

  def getFileContentsWithoutCommentLines(filename: String): List[String] = {
    (for (line <- Source.fromFile(filename).getLines
           // if !line.trim.matches("")
            if !line.trim.matches("#.*")) yield line
      ).toList
  }

  def printFile(): Unit ={
    println("Print txt without comments")
    val lines = getFileContentsWithoutCommentLines("./src/main/scala/file.txt")
    for (line <- lines){
      println(line)
    }
  }


  def main(args: Array[String]): Unit = {



  }

}
