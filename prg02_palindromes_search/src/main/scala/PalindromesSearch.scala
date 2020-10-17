/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prg02 - PalindromesSearch
 * Student Name: Austin Gailey
 * 
 * Description:
 * This program takes two numbers, n and m, and generates all possible pallindromic sums
 * of n, which include m.
 * 
 * EX:
 * n = 6, m = 2
 * 
 * All possible palindromic sums of n:
 * (6)
 * (3, 3)
 * (1, 4, 1)
 * (2, 2, 2)
 * (1, 2, 2, 1)
 * (2, 1, 1, 2)
 * (1, 1, 2, 1, 1)
 * (1, 1, 1, 1, 1, 1)
 * 
 * All possible sums which include m:
 * (2, 2, 2)
 * (1, 2, 2, 1)
 * (2, 1, 1, 2)
 * (1, 1, 2, 1, 1)
 *
 * Results are either counted, timed & printed to the console
 * or all included palindromes are saved to an output file
 * 
 */
import java.io.FileWriter
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.annotation.meta.param

object PalindromesSearch {

  val OUTPUT_FILE_NAME = "output.txt"
  var saveToFile:Boolean = false
  var count:Int = 0

  def main(args: Array[String]): Unit = {
    initProject(args)
    val start = System.currentTimeMillis()
    createPartitions(args(0).toInt,args(1).toInt)
    val end = System.currentTimeMillis()
    finishProject(end-start)
  }

  /**
    * Because a palindrome is mirrored on either side, only 1/2 n permutations 
    * need to be generated.
    * The partition creates partitions for all sums (n+1)/2
    * 
    * Great reference for partitioning:
    * https://stackoverflow.com/questions/10035752/elegant-python-code-for-integer-partitioning
    * 
    * @param n
    * @param m
    */
  def createPartitions(n:Int,m:Int):Unit = {
    val k = (n+1)/2
    val end = scala.math.pow(2,k-1).toInt
    for(i <- 0 until end){
      var j:Int = i
      var count = 1
      var partition:ArrayBuffer[Int] = ArrayBuffer()
      for(s <- 0 until k){
        if(j % 2 == 0){
          partition += count
          count = 1
        }else{
          count += 1
        }
        j >>= 1
      }
      if(partition != ArrayBuffer.empty) addPalindromeByPartition(partition.toArray,n, m)
    }
  }

  /**
    * Generates palindromes based on partition
    * 
    * @param partition
    * @param n
    * @param m
    */
  def addPalindromeByPartition(partition:Array[Int],n:Int, m:Int){
    if(n % 2 == 1){
      var palindrome:Array[Int] = (partition.slice(0,partition.length-1)
        ++(Array[Int](partition(partition.length-1)*2-1))
        ++partition.slice(0,partition.length-1).reverse)
      checkPalindrome(palindrome, m)
    }else{
      var palindrome = (partition.slice(0,partition.length-1)
        ++(Array[Int](partition(partition.length-1)*2))
        ++partition.slice(0,partition.length-1).reverse)
      checkPalindrome(palindrome, m)
      palindrome = (partition.slice(0,partition.length)
        ++partition.slice(0,partition.length).reverse)
      checkPalindrome(palindrome, m)
    }
  }

  /**
    * Check if palindrome contains m
    *
    * @param palindrome
    * @param m
    */
  def checkPalindrome(palindrome:Array[Int], m:Int){
    if(palindrome.contains(m)){
      count+= 1
      print(palindrome.mkString(",").toString)
    }
  }

  /**
    * Prepares project output & console messages
    * @param args
    */
  def initProject(args:Array[String]):Unit = {
    System.out.println("Welcome to the palindromic sequence project!")
      if(args.length == 0){
        System.out.println("Use: java PalindromesSearch n m [y] \n" +
          "[y]: when informed, all palindromic sequences must be saved to a file")
        System.exit(1)
      }
      try{
        if(args(2).equals("y")){
          saveToFile = true
          initOutput()
        }
      }catch{
        case e: Exception =>
      }
      System.out.println("Parameter n = " + args(0) + " \nParameter m = " + args(1))
  }

  /**
    * Calculates time to run project and prints finishing messages
    * @param time
    */
  def finishProject(time:Long):Unit = {
    if(!saveToFile){
      System.out.println("Number of palindromic sequences found: "+ count 
      + " \nIt took me " + time + "ms")
    }else{
      System.out.println("Done!")
    }
  }

  /**
    * Appends line to output file
    *
    * @param line
    */
  def print(line:String):Unit = {
    if(!saveToFile) return
    val fw = new FileWriter(OUTPUT_FILE_NAME, true)
    try {
      fw.write(line + "\n")
    } finally fw.close() 
  }

  /**
    * Initializes output file
    */
  def initOutput(){
    val fw = new FileWriter(OUTPUT_FILE_NAME, false)
    try {
      fw.write("")
    } finally fw.close() 
  }
}
