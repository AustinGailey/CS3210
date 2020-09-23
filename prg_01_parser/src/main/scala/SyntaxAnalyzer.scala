import SyntaxAnalyzer.{GRAMMAR_FILENAME, SLR_TABLE_FILENAME}

import scala.collection.mutable.ArrayBuffer
// PrintWriter
import java.io._

/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: prg_01_parser
 * Student: Austin Gailey
 */


class SyntaxAnalyzer(private var source: String) {

  private val it = new LexicalAnalyzer(source).iterator
  private var lexemeUnit: LexemeUnit = null
  private val grammar = new Grammar(GRAMMAR_FILENAME)
  private val slrTable = new SLRTable(SLR_TABLE_FILENAME)
  //System.out.println(slrTable.toString())

  private def getLexemeUnit() = {
    if (lexemeUnit == null)
      lexemeUnit = it.next()
    if (SyntaxAnalyzer.DEBUG)
      println("lexemeUnit: " + lexemeUnit)
  }

  def parse(): Tree = {
  
    // TODO: create a stack of trees
    val trees: ArrayBuffer[Tree] = new ArrayBuffer[Tree]

    // TODO: initialize the parser's stack of (state, symbol) pairs
    val stack: ArrayBuffer[String] = new ArrayBuffer[String]
    stack.append("0")

    while (true) {

      if (SyntaxAnalyzer.DEBUG)
        println("stack: " + stack.mkString(","))

      // TODO: update lexeme unit (if needed)
      getLexemeUnit()

      // TODO: get current state
      var state = stack.last.strip().toInt
      if (SyntaxAnalyzer.DEBUG)
        println("state: " + state)

      // TODO: get current token
      val token = lexemeUnit.getToken()
      if(SyntaxAnalyzer.DEBUG)
        println("token: " + token)
        val pw = new PrintWriter(new File("slrTableToStringOutput.txt" ))
        pw.write(slrTable.toString())
        pw.close
      // TODO: get action
      val action = slrTable.getAction(state, token)
      if (SyntaxAnalyzer.DEBUG)
        println("action: " + action)
      // TODO: if action is undefined, throw an exception
      if (action.length == 0)
        throw new Exception("Syntax Analyzer Error: " + getFollows(state) +" expected!")

      if (action(0) == 's') {

        // TODO: update the parser's stack
        stack.append(token + "")
        stack.append(action.substring(1))

        // TODO: create a new tree with the lexeme
        val tree = new Tree(lexemeUnit.getLexeme())

        // TODO: push the new tree onto the stack of trees
        trees.append(tree)

        // TODO: update lexemeUnit to null to acknowledge reading the input
        lexemeUnit = null
      }
      // TODO: implement the "reduce" operation if the action's prefix is "r"
      else if (action(0) == 'r') {

        // TODO: get the production to use
        val index = action.substring(1).toInt
        val lhs = grammar.getLHS(index)
        val rhs = grammar.getRHS(index)

        // TODO: update the parser's stack
        stack.dropRightInPlace(rhs.length * 2)
        state = stack.last.strip().toInt
        stack.append(lhs)
        stack.append(slrTable.getGoto(state, lhs))

        // TODO: create a new tree with the "lhs" variable as its label
        val newTree = new Tree(lhs)

        // TODO: add "rhs.length" trees from the right-side of "trees" as children of "newTree"
        for (tree <- trees.drop(trees.length - rhs.length))
          newTree.add(tree)

        // TODO: drop "rhs.length" trees from the right-side of "trees"
        trees.dropRightInPlace(rhs.length)

        // TODO: append "newTree" to the list of "trees"
        trees.append(newTree)
      }
      // TODO: implement the "accept" operation
      else if (action.equals("acc")) {

        // TODO: create a new tree with the "lhs" of the first production ("start symbol")
        val newTree = new Tree(grammar.getLHS(0))

        // TODO: add all trees as children of "newTree"
        for (tree <- trees)
          newTree.add(tree)

        // TODO: return "newTree"
        return newTree
      }
      else
        throw new Exception("Syntax Analyzer Error!")
    }
    throw new Exception("Syntax Analyzer Error!")
  }

  def getTokenName(tokenValue: Int):String = {
    tokenValue match{
      case 0 => return "EOF"
      case 1 => return "identifier"
      case 2 => return "int_literal"
      case 3 => return "program"
      case 4 => return "var"
      case 5 => return "type"
      case 6 => return "boolean_type"
      case 7 => return "begin"
      case 8 => return "end"
      case 9 => return "read"
      case 10 => return "write"
      case 11 => return "if"
      case 12 => return "then"
      case 13 => return "else"
      case 14 => return "while"
      case 15 => return "do"
      case 16 => return "bool_literal"
      case 17 => return ""
      case 18 => return "add_op"
      case 19 => return "sub_op"
      case 20 => return "mul_op"
      case 21 => return "div_op"
      case 22 => return "greaterthan_op"
      case 23 => return "lesserthan_op"
      case 24 => return "equals_op"
      case 25 => return "lesser_equals_op"
      case 26 => return "greater_equals_op"
      case 27 => return "open_par"
      case 28 => return "close_par"
      case 29 => return "period_punc"
      case 30 => return "comma_punc"
      case 31 => return "semicolon_punc"
      case 32 => return "colon_punc"
      case 33 => return "assign_punc"
    }
  }

  def getFollows(state: Integer):String = {
    //for each goto in the state, return the potential action
    var follows:String = ""
    for(i <- 0 until 34){
      try{
        if(slrTable.getAction(state,i) != ""){
          if(follows == ""){
            follows += getTokenName(i)
          }
          follows += ", " + getTokenName(i)
        }
      }catch{
        case e: Exception =>
      }
      
    }
    return follows
  } 
}

object SyntaxAnalyzer {

  val GRAMMAR_FILENAME   = "grammar.txt"
  val SLR_TABLE_FILENAME = "slr_table.csv"

  val DEBUG = true

  def main(args: Array[String]): Unit = {
    // check if source file was passed through the command-line
    if (args.length != 1) {
      print("Missing source file!")
      System.exit(1)
    }

    val syntaxAnalyzer = new SyntaxAnalyzer(args(0))
    val parseTree = syntaxAnalyzer.parse()
    print(parseTree)
  }
}