package com.gslab.carrentalsystem.carbooking

class Booked_Car {
  var bookedCustIds:List[String]=List()
  var bookedCarIds:List[Int]=List()
  def bookCars(custid:String,custname:String,cid:Int) =
  {
    bookedCustIds=bookedCustIds:+custid
    bookedCarIds=bookedCarIds:+cid
  }
}
