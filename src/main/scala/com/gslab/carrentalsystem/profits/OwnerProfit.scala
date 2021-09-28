package com.gslab.carrentalsystem.profits

import com.gslab.carrentalsystem.utils.LoggerUtil._
import org.apache.log4j.Logger

import scala.io.Source

class OwnerProfit {
  val logger = Logger.getLogger(this.getClass.getName)

  def getProfitData(): String =
  {
    val file = "CSV/book_car.csv"
    var inputBuffer = new StringBuffer
    //println(file)
    val f=Source.fromFile(file)
    var line:String=""
    for(line<-f.getLines)
    {
      inputBuffer.append(line)
      inputBuffer.append('\n')
    }
    inputBuffer.toString
  }
  def getProfitDatewise(): Unit =
  {
    var r=getProfitData()
    import scala.collection.mutable.ListBuffer
    var p=r.split("\n").toList
    var t=new ListBuffer[List[String]]
    var datechargedata:List[List[String]]=List()
    var e:Int=0
    e=e-1
    for(i<-0 to p.size-1)
    {

      var str=p(i).split(",").toList
      var ts=str(4)+","+str(5)+","+str(6)
      datechargedata=datechargedata:+List(ts)

    }
    calculateProfit(datechargedata)

  }
  def dateDiff(fromDate:String,toDate:String): Int =
  {
    import java.util.Date
    import java.util.concurrent.TimeUnit
    val d2 = new Date(fromDate)
    val d1=new Date(toDate)
    var r=TimeUnit.MILLISECONDS.toDays(d1.getTime - d2.getTime)
    if(r<0)
      {
        var r=TimeUnit.MILLISECONDS.toDays(d2.getTime - d1.getTime)

      }
    //println("Diffetenrce is "+r)
    r.toInt
  }
  def calculateProfit(datechargedata:List[List[String]]): Unit =
  {
    var chargesum:Double=0
    var k:Int=0
    var j:Int=0
    logger.info("Profit Calulated by Admin")
    for (i<- 0 to datechargedata.size-1)
    {
      //var ls=datechargedata(i).toList.toString().split(",").toList
      var ls=datechargedata(i).toString
      var result = ls.replaceAll("List\\(","")
      ls=result.replace(")","")
      var sp=ls.split(",").toList
      var fromdate=sp(0)
      var todate=sp(1)
      var chargeamt=sp(2)
      chargesum=chargesum+chargeamt.toDouble
      var r=dateDiff(fromdate,todate)
      k=k+r
      j+=1
      if(k>=29)
      {
        //Assuming he get profit of 25% per month amount
        chargesum=(chargesum*25)/100
        println("Profit in the "+j+"st month is :"+ chargesum)
        k=0
      }


    }
  }

}
