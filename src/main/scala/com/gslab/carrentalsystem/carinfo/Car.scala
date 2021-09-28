package com.gslab.carrentalsystem.carinfo

import java.io.{FileWriter, RandomAccessFile}

import scala.io.{Source, StdIn}
import java.time.LocalDate

import com.gslab.carrentalsystem.utils.LoggerUtil._
import org.apache.log4j.Logger

import scala.util.control.Breaks.{break, breakable}



  case class Car_Info(carId:List[Int],brand:List[String],color:List[String],price:List[Double],model:List[String],typeofCar:List[String],charge:List[Double],carReading:List[Int],isBooked:List[Int],profitCharge:List[Double])
  class Car {
    val logger = Logger.getLogger(this.getClass.getName)

    var carId: List[Int] = List()
    var brand: List[String] = List()
    var color: List[String] = List()
    var price: List[Double] = List()
    var model: List[String] = List()
    var typeofCar: List[String] = List()
    var charge: List[Double] = List()
    var profitCharge: List[Double] = List()
    var carReading: List[Int] = List()
    var isBooked: List[Int] = List()

    def addCarData(carinfoData: List[List[String]]): Car_Info = {
      //println(carinfoData)

      carId = carinfoData.map(r => r(0)).map(_.toString.toInt)
      //println(carId)
      brand = carinfoData.map(r => r(1)).map(_.toString)
      color = carinfoData.map(r => r(2)).map(_.toString)
      price = carinfoData.map(r => r(3)).map(_.toString.toDouble)
      model = carinfoData.map(r => r(4)).map(_.toString)
      typeofCar = carinfoData.map(r => r(5)).map(_.toString)
      charge = carinfoData.map(r => r(6)).map(_.toString.toDouble)

      carReading = carinfoData.map(r => r(7)).map(_.toString.toInt)
      isBooked = carinfoData.map(r => r(8)).map(_.toString.toInt)
      profitCharge = carinfoData.map(r => r(8)).map(_.toString.toInt)

      var carData = Car_Info(carId, brand, color, price, model, typeofCar, charge, carReading, isBooked,profitCharge)

      carData
    }

    def listAllCars(): Unit = {
      logger.info("Car Details Seen")
      println("------------All Car Details--------------------")
      for (i <- 0 to brand.size - 1) {
        println(i + "|" + brand(i))
      }
    }

    def searchCars(name: String): Unit = {
      logger.info(s"User Searching ${name} Car")
      println("----------------Available Cars----------------")
      var isLive:Int=0
      for (i <- 0 to brand.size - 1) {
        if (brand(i) == name && isBooked(i) != 1) {
          println(carId(i) + "|" + brand(i) + "|" + color(i) + "|" + price(i) + "|" + model(i) + "|" + typeofCar(i) + "|" + charge(i) + "|" + carReading(i))
          isLive = 1
        }
      }
        if(isLive==0)
          {
            logger.info("Car not Found")
            print("--------Sorry..This Car is not available.")
            println(" Try Another Car--------------")
          }


    }

    def updateCars(carid: Int, newcharge: Double): Unit = {
      for (i <- 0 to brand.size - 1) {
        if (carId(i) == carid) {
          println("r")
          println(carId(i) + "|" + brand(i) + "|" + color(i) + "|" + price(i) + "|" + model(i) + "|" + typeofCar(i) + "|" + charge(i) + "|" + carReading(i))
          charge = charge.updated(carid, newcharge)
        }
        val writer = new FileWriter("CSV/car.csv")

        for (i <- 0 to carId.size - 1) {


          var str = carId(i) + "," + brand(i) + "," + color(i) + "," + price(i) + "," + model(i) + "," + typeofCar(i) + "," + charge(i) + "," + carReading(i) + "," + isBooked(i)+","+profitCharge(i)
          if (i != carId.size - 1) {
            writer.write(str + "\n")
          }
          else {
            writer.write(str)
          }

        }
        writer.close()


      }

    }

    def bookCars( custId: String,custname:String, cid: Int): Unit = {
      //val writer = new FileWriter("/home/gslab/BigData/CRS_1/CSV/book_car.csv")
      var fromDate = StdIn.readLine.toString
      var toDate = StdIn.readLine.toString
      var charges="0".toInt
      //val fileStore = new RandomAccessFile("/home/gslab/BigData/CRS_1/CSV/book_car.csv", "rw")
      import java.io.RandomAccessFile
      //val raf = new RandomAccessFile(f, "rw")
      //val f= new File("/home/gslab/BigData/CRS_1/CSV/book_car.csv")
      val f=Source.fromFile("CSV/book_car.csv")
      var inputBuffer = new StringBuffer
      var line:String=""
      for(line<-f.getLines)
      {
        inputBuffer.append(line)
        inputBuffer.append('\n')
      }

      //println(inputBuffer)
      var filelen=inputBuffer.toString.length
      f.close()
      //println("############"+filelen)
      val fileStore = new RandomAccessFile("CSV/book_car.csv", "rw")

      fileStore.seek(filelen)
      //fileStore.write(bytes)
      var str = custId + "," + custname + "," + carId(cid) + "," + brand(cid)+","+fromDate+","+toDate+","+charges+"\n"
     // //writer.append(str)
      fileStore.writeBytes(str)
      //writer.write(str + "\n")
      fileStore.close()
      logger.info("Car has been booked")
      println("Car Has Been Booked...Happy Journy")
      //println("Need to Update Flag in File and List of Cars so it is marked as booked")

      isBooked = isBooked.updated(cid, 1)
      //writer.close()
      val writer1 = new FileWriter("CSV/car.csv")

      for (i <- 0 to carId.size - 1) {


        var str1 = carId(i) + "," + brand(i) + "," + color(i) + "," + price(i) + "," + model(i) + "," + typeofCar(i) + "," + charge(i) + "," + carReading(i) + "," + isBooked(i)+","+profitCharge(i)
        if (i != carId.size - 1) {
          writer1.write(str1 + "\n")
        }
        else {
          writer1.write(str1)
        }

      }

      writer1.close()
    }

    def addCarDetails(filename: String): Unit = {

      var CarId = brand.size + 1
      println(brand.size)
      println("Enter brand of car: ")
      var cbrand = StdIn.readLine.toString

      println("Enter color: ")
      var carcolor = StdIn.readLine.toString

      println("Enter price: ")
      var cprice = StdIn.readLine.toDouble

      println("Enter model")
      var cmodel = StdIn.readLine.toString

      println("Enter type of car: ")
      var ctypeofcar = StdIn.readLine.toString

      println("Enter charge of the car:")
      var carcharge = StdIn.readLine.toDouble

      println("Enter Current Reading:")
      var carReading = StdIn.readLine.toInt
      println("Enter Status")
      var cstatus = StdIn.readLine.toInt

      var s = new StringBuilder("")
      s = s.append(CarId.toString)
      s = s.append(",")
      s = s.append(cbrand)
      s = s.append(",")
      s = s.append(carcolor)
      s = s.append(",")
      s = s.append(cprice.toString)
      s = s.append(",")
      s = s.append(cmodel.toString)
      s = s.append(",")
      s = s.append(ctypeofcar.toString)
      s = s.append(",")
      s = s.append(carcharge.toString)
      s = s.append(",")
      s = s.append(carReading.toString)
      s = s.append(",")
      s = s.append(cstatus.toString)
      //println(s)

      val cardetails = s.mkString("")
      logger.info("Car Details Added")
      writeCarData(filename, cardetails)
    }

    def writeCarData(filename: String, cardata: String): Unit = {
      val writer = new FileWriter(filename, true)

      for (i <- 0 to carId.size - 1) {
        //var str = carId(i) + "," + brand(i) + "," + color(i) + "," + price(i) + "," + model(i) + "," + typeofCar(i) + "," + charge(i) + "," + carReading(i)+","+isBooked(i)
        if (i == carId.size - 1) {
          writer.write(cardata + "\n")
        }


      }
      logger.info(s"Car Data written ot ${filename}")
      writer.close()
    }

    def deleteCars(n: Int, filename: String): Unit = {

      for (i <- 0 to brand.size - 1) {
        if (carId(i) == n) {
          println("r")
          println(carId(i) + "|" + brand(i) + "|" + color(i) + "|" + price(i) + "|" + model(i) + "|" + typeofCar(i) + "|" + charge(i) + "|" + carReading(i))
          isBooked = isBooked.updated(n, -1)
        }
        val writer = new FileWriter(filename)

        for (i <- 0 to carId.size - 1) {
          var str = carId(i) + "," + brand(i) + "," + color(i) + "," + price(i) + "," + model(i) + "," + typeofCar(i) + "," + charge(i) + "," + carReading(i) + "," + isBooked(i)
          if (i != carId.size - 1) {
            writer.write(str + "\n")
          }
          else {
            writer.write(str)
          }

        }
        logger.info(s"Car details removed")
        writer.close()


      }
    }
  }


