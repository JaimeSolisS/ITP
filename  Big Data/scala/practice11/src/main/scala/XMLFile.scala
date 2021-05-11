import scala.xml.XML

/*
Commonly used methods of the Elem class

Method                 Description
------                 -----------

x \ "div"              Searches the XML literal x for elements of type <div>.
                       Only searches immediate child nodes (no grandchild or “descendant” nodes).

x \\ "div"             Searches the XML literal x for elements of type <div>. Returns matching
                       elements from child nodes at any depth of the XML tree.

x.attribute("class")   Returns the value of the given attribute in the current node.
                       <a x="10" y="20">foo</a>.attribute("x")   // returns Some(10).

x.attributes           Returns all attributes of the current node, prefixed and unprefixed,
                       in no particular order.
                       scala> <a x="10" y="20">foo</a>.attributes
                       res0: scala.xml.MetaData =  x="10" y="20"

x.child                Returns the children of the current node.
                       <a><b>foo</b></a>.child   // returns <b>foo</b>.

x.copy(...)            Returns a copy of the element, letting you replace data during the
                       copy process.

x.label                The name of the current element.
                       <a><b>foo</b></a>.label   // returns a.

x.text                 Returns a concatenation of text(n) for each child n.

x.toString             Emits the XML literal as a String.
                       Use scala.xml.PrettyPrinter to format the output, if desired.
 */

object XMLFile {

  def readFile(file : String): Unit ={

    val xml = XML.loadFile(file)

    //get book titles
    for (title <- xml \\ "title"){
      println(title.text)
    }
  }



}
