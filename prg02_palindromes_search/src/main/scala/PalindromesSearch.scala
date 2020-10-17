/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prg02 - PalindromesSearch
 * Student(s) Name(s): Austin Gailey
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
    palindromes(args(0).toInt,args(1).toInt)
    val end = System.currentTimeMillis()
    finishProject(end-start)
  }

  def palindromes(n:Int, m:Int):Unit = {
    for(partition <- orderedPartition(((n+1)/2))){
      if(n % 2 == 1){
        var palindrome:Array[Int] = (partition.slice(0,partition.length-1)
                         ++(Array[Int](partition(partition.length-1)*2-1))
                         ++partition.slice(0,partition.length-1).reverse)
        if(palindrome.contains(m)){
          count+= 1
          print(palindrome.mkString(",").toString)
        }
      }else{
        var palindrome = (partition.slice(0,partition.length-1)
                         ++(Array[Int](partition(partition.length-1)*2))
                         ++partition.slice(0,partition.length-1).reverse)
        if(palindrome.contains(m)){
          count+= 1
          print(palindrome.mkString(",").toString)
        }
        if(partition.contains(m)){
          palindrome = (partition.slice(0,partition.length)
                         ++partition.slice(0,partition.length).reverse)
          count += 1
          print(palindrome.mkString(",").toString)
        }
      }
    }
  }

  def orderedPartition(k:Int):Array[Array[Int]] = {
    var allParts = ArrayBuffer[Array[Int]]()
    for(i <- 0 until scala.math.pow(2,k-1).toInt){
      var m:Int = i
      var count = 1
      var part:ArrayBuffer[Int] = ArrayBuffer()
      for(s <- 0 until k){
        if(m % 2 == 0){
          part += count
          count = 1
        }else{
          count += 1
        }
        m >>= 1
      }
      if(part != ArrayBuffer.empty) allParts += part.toArray
    }
    return allParts.toArray
  }

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

  def finishProject(time:Long):Unit = {
    if(!saveToFile){
      System.out.println("Number of palindromic sequences found: "+ count 
      + " \nIt took me " + time + "ms")
    }else{
      System.out.println("Done!")
    }
  }

  def print(line:String):Unit = {
    if(!saveToFile) return
    val fw = new FileWriter(OUTPUT_FILE_NAME, true)
    try {
      fw.write(line + "\n")
    } finally fw.close() 
  }

  def initOutput(){
    val fw = new FileWriter(OUTPUT_FILE_NAME, false)
    try {
      fw.write("")
    } finally fw.close() 
  }
}
