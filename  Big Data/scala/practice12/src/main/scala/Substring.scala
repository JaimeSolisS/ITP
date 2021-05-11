import java.io.File
import scala.collection.mutable.ListBuffer

object Substring {

  def getListOfFiles(dir: String):List[File] = {
    val file = new File(dir)

    if (file.exists && file.isDirectory) {
      file.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  def files(): Unit ={
    println("Print files of direcotory")
    val files = getListOfFiles("./src/main/scala")

    //mutable list
    var mfiles = new ListBuffer[String]
    for (file <- files) {
      mfiles +=file.getName()
    }

    for (file <- mfiles){
      print(file + ", ")
    }
  }

  def main(args: Array[String]): Unit = {

    }



}
