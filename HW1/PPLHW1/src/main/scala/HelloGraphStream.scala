import org.graphstream.graph.{Edge, Node}
import org.graphstream.graph.implementations.{SingleGraph, SingleNode}
import org.graphstream.ui.spriteManager.SpriteManager

object HelloGraphStream {

  val USER_DIR = System.getProperty("user.dir")
  val STYLE    = "stylesheet.css"
  val graph = new SingleGraph("hello")


  def main(args: Array[String]): Unit = {

    // create graph
    System.out.println("url('file://" + USER_DIR + "/" + STYLE + "')")
    graph.addAttribute("ui.stylesheet", "url('file:" + USER_DIR + "/" + STYLE + "')")
    System.out.println("stylesheet = "+graph.getAttribute("ui.stylesheet").toString())
    graph.addAttribute("ui.antialias")

    // create nodes
    var node: Node = graph.addNode("A")
    node.addAttribute("ui.label", "A")
    node = graph.addNode("B")
    node.addAttribute("ui.label", "B")
    node = graph.addNode("C")
    node.addAttribute("ui.label", "C")

    // create edges
    var edge: Edge = graph.addEdge("AB", "A", "B", true)
    edge = graph.addEdge("BC", "B", "C", true)
    // alternatively
    graph.addEdge[Edge]("CA", "C", "A", true)

    // display graph
    graph.display()
  }

}
