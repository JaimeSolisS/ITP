import scala.io.Source

object Trim {

  def getFileContentsWithoutCommentLines(filename: String): List[String] = {
    (for (line <- Source.fromFile(filename).getLines
          if !line.trim.matches("")
          if !line.trim.matches("#.*")) yield line.trim.replaceAll(" +", " ")
      ).toList
  }

  def printFile(): Unit ={
    println("Trim spaces")
    val lines = getFileContentsWithoutCommentLines("./src/main/scala/file.txt")
    for (line <- lines){
      println(line)
    }
  }


  def main(args: Array[String]): Unit = {

  printFile()

  }

}
