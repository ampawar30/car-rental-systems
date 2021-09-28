package com.gslab.carrentalsystem.billing
import java.io.{EOFException, FileWriter, RandomAccessFile}
import java.time.LocalDate


///home/gslab/BigData/CRS_1/src/main/scala/LoggerUtil.scala
import com.gslab.carrentalsystem.carinfo.Car
import org.apache.log4j.Logger
import scala.io.{Source, StdIn}
class IssueCharges {
  val logger = Logger.getLogger(this.getClass.getName)
  final val rate_per_km:Double=7.9
  def makePayment(cobj:Car,carId:Int,custname:String): Unit =
  {
    var cid:Int=3
    findCustomerBill(cobj)
    println("Enter The Last Reading For Booked Car")

    var lastReading = StdIn.readLine.toString.toInt

    var totalCharge=(lastReading-cobj.carReading(cid))*rate_per_km
    updateUserCharge("CSV/car.csv",cid,totalCharge,cobj,carId,custname)

      logger.info(s"Total Charges for Customer ${custname} is ${Math.abs(totalCharge)}")
      println("---------------Payment Details----------------")
      println("Total Charge" +Math.abs(totalCharge))

    //println(cobj.brand)
  }
  def updateUserCharge(f:String,cid:Int,totalamt:Double,cobj:Car,carId:Int,custname:String): Unit = {

    var record: String = ""
    val fileStore = new RandomAccessFile("CSV/book_car.csv", "rw")
    var i: Int = 0
    var strcid = cid.toString
    val file = Source.fromFile("CSV/book_car.csv")
    var inputBuffer = new StringBuffer
    import util.control.Breaks._
    breakable {
      for (line <- file.getLines) {
        if (line.contains("10")) {
          logger.info("Break From updateUserCharges")
          break
        }
        else {
          inputBuffer.append(line)
          inputBuffer.append("\n")
        }
        // println(inputBuffer.length)
      }

    }
    if (inputBuffer.length() <= 0) {
      fileStore.seek(0)
    }
    else {
      i = inputBuffer.length - 1
      fileStore.seek(inputBuffer.length - 1)
    }

    var EOF: Int = 1
    EOF = EOF - 2
    var EOL='\n'
    var num:Int=0
    //println(r.toByte)
    //println(r.toChar)

    breakable {
      while (true) {
        //if(fileStore.read.toChar==null)

          if(fileStore.read.toByte!=EOL.toByte) {
            //println(fileStore.read.toChar)
           // println(fileStore.readByte.toChar)
           // println(fileStore.read.toString)
            fileStore.seek(i)
            num=i
            i = i + 1
          }
          else
            {
              break
            }
      }
    }
    //fileStore.seek(1)

    //println(fileStore.readByte.toChar)
    //println(inputBuffer.length)
    fileStore.seek(num-1)
    var tot=totalamt+"\n"
    fileStore.writeBytes(tot)

    fileStore.close()

  }

  def findCustomerBill(cobj:Car): Unit =
  {
    for(i<- 0 to cobj.carId.size-1)
    {
      if(cobj.isBooked(i)==1)
      {
        println("|"+cobj.carId(i)+"|"+cobj.brand(i)+"|"+cobj.carReading(i))
      }
    }
  }
}
