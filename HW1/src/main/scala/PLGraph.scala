import java.io.File
import java.util.Scanner

import org.graphstream.graph.Node
import org.graphstream.graph.implementations.MultiGraph


/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Student: Austin Gailey
 * Description: Homework 01 - PLGraph
 */

object PLGraph {

  val PL_CSV_FILE = "pl.csv"
  val USER_DIR: String = System.getProperty("user.dir")
  val STYLE       = "stylesheet.css"
  val graph = new MultiGraph("PL")

  def main(args: Array[String]): Unit = {
    //graph.addAttribute("ui.stylesheet", "url('file://" + USER_DIR + "/" + STYLE + "')") //Had to change path for Windows
    graph.addAttribute("ui.stylesheet", "url('file:" + USER_DIR + "/" + STYLE + "')")
    graph.addAttribute("ui.antialias")
    try{
      val plFile: File = new File(USER_DIR + "/" + PL_CSV_FILE)
      val sc: Scanner  = new Scanner(plFile)
      while(sc.hasNext()){
        addToGraph(sc.nextLine())
      }
    }catch{
      case ex: Exception => 
    }
    graph.display()
  }

  def addToGraph(str: String): Unit ={
    val strArray:Array[String] = str.split(',')
    addNode(strArray(0))
    addNode(strArray(1))
    addDirectedEdge(strArray(0),strArray(1))
  }

  def addNode(str:String): Unit ={
    if(graph.getNode(str) == null){
      val node: Node = graph.addNode(str)
      node.addAttribute("ui.label", str)
    }
  }

  def addDirectedEdge(s1:String, s2:String): Unit ={
    graph.addEdge(s1+s2,s1,s2,true)
  }
}
