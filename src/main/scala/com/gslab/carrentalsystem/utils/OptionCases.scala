package com.gslab.carrentalsystem.utils

object OptionCases {

  //var A:Map[Char,Int] =
  val mapOptions:Map[String,Int]=Map("listAllCars"->1,"addCar"->2,"issuePayment"->3,"searchCar"->3,"updateCar"->4,

  "deleteCar"->5,
  "profitPerMonth"->6,
  "adminLogout"->7,
  "customerSearchCar"->1,
  "bookCar"->2,
    "customerLogout"->4
  )
  val LIST_ALL_CARS:Int=1
  val ADD_CAR:Int=2
  val ISSUE_PAYMENT:Int=3
  val SEARCH_CAR:Int=1
  val SEARCH_CAR_ADMIN:Int=3
  val updateCar:Int=4
  val DELETE_CAR:Int=5
  val PROFIT_PER_MONTH:Int=6
  val ADMIN_LOGOUT:Int=7
  val CUSTOMER_SEARCH_CAR:Int=1
  val BOOK_CAR:Int=2
  val CUSTOMER_LOGOUT:Int=4

}
