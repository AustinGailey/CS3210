/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Homework 03 - Token
 */

object Token extends Enumeration {
  val EOF                   = 0
  val IDENTIFIER            = 1
  val INT_LITERAL           = 2
  val PROGRAM               = 3
  val VAR                   = 4
  val TYPE                  = 5
  val BOOLEAN_TYPE          = 6
  val BEGIN                 = 7
  val END                   = 8
  val READ                  = 9
  val WRITE                 = 10
  val IF                    = 11
  val THEN                  = 12
  val ELSE                  = 13
  val WHILE                 = 14
  val DO                    = 15
  val BOOL_LITERAL          = 16
  val ADD_OP                = 18
  val SUB_OP                = 19
  val MUL_OP                = 20
  val DIV_OP                = 21
  val GREATERTHAN_OP        = 22
  val LESSERTHAN_OP         = 23
  val EQUALS_OP             = 24
  val LESSER_EQUALS_OP      = 25
  val GREATER_EQUALS_OP     = 26
  val OPEN_PAR              = 27
  val CLOSE_PAR             = 28
  val PERIOD_PUNC           = 29
  val COMMA_PUNC            = 30
  val SEMICOLON_PUNC        = 31
  val COLON_PUNC            = 32
  val ASSIGN_PUNC/**':='**/ = 33
}
