import LexicalAnalyzer.{WORD_TO_TOKEN}

import scala.io.Source

/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prg01 - Lexical Analyzer
 * Student(s) Name(s): Austin Gailey
 */

class LexicalAnalyzer(private var source: String) extends Iterable[LexemeUnit] {

  private var input = ""
  for (line <- Source.fromFile(source).getLines)
    input += line + "\n"

  // determines the class of a given character
  private def getCharClass(c: Char): CharClass.Value = {
    if (LexicalAnalyzer.LETTERS.contains(c))
      CharClass.LETTER
    else if (LexicalAnalyzer.DIGITS.contains(c))
      CharClass.DIGIT
    else if (LexicalAnalyzer.BLANKS.contains(c))
      CharClass.BLANK
    else if (c == '+' || c == '-' || c == '*' || c == '/' ||  c == '>' || c == '<' || c == '=')
      CharClass.OPERATOR
    else if (c == '.' || c == ',' || c == ';' || c == ':')
      CharClass.PUNCTUATOR
    else if (c == '(' || c == ')')
      CharClass.DELIMITER
    else
      CharClass.OTHER
  }

  // reads the input until a non-blank character is found, returning the input updated
  private def readBlanks: Unit = {
    var foundNonBlank = false
    while (input.length > 0 && !foundNonBlank) {
      val c = input(0)
      if (getCharClass(c) == CharClass.BLANK)
        input = input.substring(1)
      else
        foundNonBlank = true
    }
  }

  def iterator: Iterator[LexemeUnit] = {
    new Iterator[LexemeUnit] {

      override def hasNext: Boolean = {
        readBlanks
        input.length > 0
      }

      override def next(): LexemeUnit = {
        if (!hasNext)
          new LexemeUnit("", Token.EOF)
        else {
          var lexeme = ""
          readBlanks
          if (input.length == 0)
            new LexemeUnit(lexeme, Token.EOF)
          else {
            var c = input(0)
            var charClass = getCharClass(c)

            // TODO: finish the code

            // TODO: recognize a letter followed by letters (or digits) as an identifier
            if (charClass == CharClass.LETTER) {
              input = input.substring(1)
              lexeme += c
              var noMoreLetterDigits = false
              while (!noMoreLetterDigits) {
                if (input.length == 0)
                  noMoreLetterDigits = true
                else {
                  c = input(0)
                  charClass = getCharClass(c)
                  if (charClass == CharClass.LETTER || charClass == CharClass.DIGIT) {
                    input = input.substring(1)
                    lexeme += c
                  }
                  else
                    noMoreLetterDigits = true
                }
              }
              //recognize reserved words
              try{
                var token = WORD_TO_TOKEN(lexeme)
                if(token != null){
                  return new LexemeUnit(lexeme, token)
                }
              }catch{
                case e: Exception =>
              }
              return new LexemeUnit(lexeme, Token.IDENTIFIER)
            }

            // TODO: recognize multiple digits as a literal
            if (charClass == CharClass.DIGIT) {
              input = input.substring(1)
              lexeme += c
              var noMoreDigits = false
              while (!noMoreDigits) {
                if (input.length == 0)
                  noMoreDigits = true
                else {
                  c = input(0)
                  charClass = getCharClass(c)
                  if (charClass == CharClass.DIGIT) {
                    input = input.substring(1)
                    lexeme += c
                  }
                  else
                    noMoreDigits = true
                }
              }
              return new LexemeUnit(lexeme, Token.INT_LITERAL)
            }

            // TODO: recognize operators
            if (charClass == CharClass.OPERATOR) {
              input = input.substring(1)
              lexeme += c
              c match {
                case '+' => return new LexemeUnit(lexeme, Token.ADD_OP)
                case '-' => return new LexemeUnit(lexeme, Token.SUB_OP)
                case '*' => return new LexemeUnit(lexeme, Token.MUL_OP)
                case '/' => return new LexemeUnit(lexeme, Token.DIV_OP)
                case '>' => {
                  if(input(0) == '=') {
                    lexeme += input(0)
                    input = input.substring(1)
                    return new LexemeUnit(lexeme, Token.GREATER_EQUALS_OP)
                  }
                  return new LexemeUnit(lexeme, Token.GREATERTHAN_OP)
                }
                // TODO: recognize '>=' and '<='
                case '<' => {
                  if(input(0) == '=') {
                    lexeme += input(0)
                    input = input.substring(1)
                    return new LexemeUnit(lexeme, Token.LESSER_EQUALS_OP)
                  }
                  return new LexemeUnit(lexeme, Token.LESSERTHAN_OP)
                }
                case '=' => return new LexemeUnit(lexeme, Token.EQUALS_OP)
              }
            }

            // TODO: recognize delimiters
            if (charClass == CharClass.DELIMITER) {
              input = input.substring(1)
              lexeme += c
              c match {
                case '(' => return new LexemeUnit(lexeme, Token.OPEN_PAR)
                case ')' => return new LexemeUnit(lexeme, Token.CLOSE_PAR)
              }
            }
            //TODO: recognize punctuators
            if (charClass == CharClass.PUNCTUATOR) {
              input = input.substring(1)
              lexeme += c
              c match {
                case '.' => return new LexemeUnit(lexeme, Token.PERIOD_PUNC)
                case ',' => return new LexemeUnit(lexeme, Token.COMMA_PUNC)
                case ';' => return new LexemeUnit(lexeme, Token.SEMICOLON_PUNC)
                case ':' => {
                  if(input(0) == '=') {
                    lexeme += input(0)
                    input = input.substring(1)
                    return new LexemeUnit(lexeme, Token.ASSIGN_PUNC)
                  }
                  return new LexemeUnit(lexeme, Token.COLON_PUNC)              
                }
              }
            }
            // throw an exception if an unrecognizable symbol is found
            throw new Exception("Lexical Analyzer Error: unrecognizable symbol found!")
          }
        }
      } // end next
    } // end 'new' iterator
  } // end iterator method
} // end LexicalAnalyzer class

object LexicalAnalyzer {
  val LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
  val DIGITS  = "0123456789"
  val BLANKS  = " \n\t"
  val WORD_TO_TOKEN = Map(
    "program"     -> Token.PROGRAM,
    "var"         -> Token.VAR ,
    "Integer"     -> Token.TYPE,
    "Boolean"     -> Token.TYPE,
    "begin"       -> Token.BEGIN, 
    "end"         -> Token.END, 
    "read"        -> Token.READ, 
    "write"       -> Token.WRITE, 
    "if"          -> Token.IF,
    "then"        -> Token.THEN, 
    "else"        -> Token.ELSE, 
    "while"       -> Token.WHILE, 
    "do"          -> Token.DO, 
    "true"        -> Token.BOOL_LITERAL,
    "false"       -> Token.BOOL_LITERAL
  )

  def main(args: Array[String]): Unit = {
    // check if source file was passed through the command-line
    if (args.length != 1) {
      print("Missing source file!")
      System.exit(1)
    }

    val lex = new LexicalAnalyzer(args(0))
    val it = lex.iterator
    while (it.hasNext) {
      val lexemeUnit = it.next()
      println(lexemeUnit)
    }
  } // end main method
} // end LexicalAnalyzer object