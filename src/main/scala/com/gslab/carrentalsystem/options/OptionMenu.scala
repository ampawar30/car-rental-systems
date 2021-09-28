package com.gslab.carrentalsystem.options

import com.csvreader.CSVReader
import com.gslab.carrentalsystem.billing.IssueCharges
import com.gslab.carrentalsystem.carbooking.Booked_Car
import com.gslab.carrentalsystem.carinfo.Car
import com.gslab.carrentalsystem.profits.OwnerProfit
import com.gslab.carrentalsystem.utils.LoggerUtil._
import com.gslab.carrentalsystem.utils.OptionCases
import org.apache.log4j.Logger

import scala.io.StdIn

class OptionMenu {
  val logger = Logger.getLogger(this.getClass.getName)
//(customerEmailInput,userOption,userinfoData, carinfodata,carfileName
  def findCustomerOption(customerEmail:String,custname:String,option: Int,userInfoData:List[List[String]],iodata: List[List[String]],filename:String): Unit = option match {

    case OptionCases.SEARCH_CAR=>
      var carinfoData1: List[List[String]]=List()
      println("Enter The Car Model Name to Find Details")
      var str = StdIn.readLine.toString
      var csvobj=new CSVReader
      carinfoData1 = csvobj.readCSV(filename)
      var temp = new Car
      
      var carobj = temp.addCarData(carinfoData1)
      temp.searchCars(str)

    case OptionCases.BOOK_CAR =>
      var carinfoData1: List[List[String]]=List()
      println("Enter Model Name")
      var mname=StdIn.readLine.toString
      var temp = new Car
      var csvobj=new CSVReader
      carinfoData1 = csvobj.readCSV(filename)
      var carobj = temp.addCarData(carinfoData1)
      temp.searchCars(mname)
      println("Please Select CarId From Above List")
      var cid=StdIn.readLine.toInt
      temp.bookCars(customerEmail,custname,cid-1)
      var bcar=new Booked_Car
      bcar.bookCars(customerEmail,custname,cid-1)
    case OptionCases.ISSUE_PAYMENT=>
      var iodata1: List[List[String]]=List()
      var temp = new Car
      var csv=new CSVReader
      iodata1 = csv.readCSV(filename)
      var cobj=temp.addCarData(iodata1)
      var pay=new IssueCharges
      pay.makePayment(temp,1,customerEmail)
    case 4 =>
      logger.info("Log-Out")
      println("Logout Me")
    case _ => println("Invalid Option")
  }
  def findOption(option: Int,iodata: List[List[String]],filename:String): Unit = option match {
    case OptionCases.LIST_ALL_CARS =>
      var carinfoData1: List[List[String]]=List()
      var csvobj=new CSVReader
      carinfoData1 = csvobj.readCSV(filename)
      var cardata = new Car
      var carobj = cardata.addCarData(carinfoData1)
      cardata.listAllCars()
    case OptionCases.ADD_CAR=>
      var carinfoData1: List[List[String]]=List()
      var cadd= new Car
      var csvobj= new CSVReader
      carinfoData1 = csvobj.readCSV(filename)
      var carobj=cadd.addCarData(carinfoData1)
      cadd.addCarDetails(filename)
    case OptionCases.SEARCH_CAR_ADMIN =>
      var carinfoData1: List[List[String]]=List()
      println("Enter The Car Model Name to Find Details")
      var str = StdIn.readLine.toString
      var csvobj=new CSVReader
      carinfoData1 = csvobj.readCSV(filename)
      var temp = new Car
      var carobj = temp.addCarData(carinfoData1)
      temp.searchCars(str)
    case OptionCases.updateCar =>
      var carinfoData1: List[List[String]]=List()
      println("Enter The CarId and New Charges")
      var carid=StdIn.readLine.toInt
      var newcharge=StdIn.readLine.toDouble
      var temp=new Car
      var csvobj=new CSVReader
      carinfoData1 = csvobj.readCSV(filename)
      var carobj = temp.addCarData(carinfoData1)
      temp.updateCars(carid,newcharge)
    case OptionCases.DELETE_CAR =>
      var carinfoData1: List[List[String]]=List()
      println("Enter The CarId")
      var n=StdIn.readLine.toInt
      var temp=new Car
      var csvobj=new CSVReader
      carinfoData1 = csvobj.readCSV(filename)
      var carobj = temp.addCarData(carinfoData1)
      temp.deleteCars(n,filename)
    case OptionCases.PROFIT_PER_MONTH =>var op=new OwnerProfit
            op.getProfitDatewise()
    case OptionCases.ADMIN_LOGOUT=>
      logger.info("Log-Out")
      println("Logout")
    case _ => println("Invalid Option")
  }
}
