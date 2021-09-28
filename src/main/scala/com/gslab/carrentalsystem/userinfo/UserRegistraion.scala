package com.gslab.carrentalsystem.userinfo
import scala.io.StdIn
import java.io.FileReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io._

import com.gslab.carrentalsystem.utils.LoggerUtil._
import org.apache.log4j.Logger

import scala.collection.mutable.ListBuffer
import scala.io.Source
case class User(UserId:List[String],Name:List[String],Age:List[Int],City:List[String])


  class UserRegistration {
    val logger = Logger.getLogger(this.getClass.getName)

    var custEmailId: List[String] = List()
    var custName: List[String] = List()
    var custAge: List[Int] = List()
    var custCity: List[String] = List()

    def assignToUser(userinfoData: List[List[String]]): Any = {
      //println(userinfoData)

      custEmailId = userinfoData.map(r => r(0)).map(_.toString)
      //println(cId)
      custName = userinfoData.map(r => r(1)).map(_.toString)
      custAge = userinfoData.map(r => r(2)).map(_.toString.toInt)
      custCity = userinfoData.map(r => r(3)).map(_.toString)

      var userData = User(custEmailId,custName,custAge,custCity)

      userData
    }

    def getUserData(emailId:String): String = {
      println("New User Please Register")
      println("--------------------------------")
      var UserId = emailId
      println(" email Id: "+UserId)
      println("Enter Name: ")
      var UserName = StdIn.readLine.toString
      println("Enter age: ")
      var UserAge = StdIn.readLine.toInt
      println("Enter city: ")
      var UserCity = StdIn.readLine.toString
      var f = ListBuffer[String]()
      f += UserId
      f += ","
      f += UserName
      f += ","
      f += UserAge.toString
      f += ","
      f += UserCity
      val final1 = f.mkString("")
      logger.info(s"User Registration ${final1}")
      return final1
      //User_data_write("CSV/user.csv",final1)
    }

    def writeUserDataToFile(filename: String, emailId:String): Unit = {
      val finalString= getUserData(emailId)
      val writer = new FileWriter(filename, true)
      writer.write(finalString + "\n")
      writer.close()

    }



}
