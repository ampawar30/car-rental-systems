import scala.io.StdIn
//import LoggerUtil._
import scala.io.Source
import com.gslab.carrentalsystem.utils.LoggerUtil._
import com.csvreader.CSVReader
import com.gslab.carrentalsystem.options.OptionMenu
import com.gslab.carrentalsystem.utils.OptionCases
import com.gslab.carrentalsystem.userinfo.UserRegistration
  object Main  {

    def displayMenu(carinfodata: List[List[String]],carfileName:String,userinfoData: List[List[String]],userfileName:String,custdata:List[String]): Unit = {
      def displayAdminMenuOption() {

        println("Welcome To Car Rental System...!")
        println("====================================")
        println("1. List All Car")
        println("2. Add Car To DataFile")
        println("3. Search For Car")
        println("4. Update Car Details")
        println("5. Delete Car")
        println("6. Profit Per Month Of Owner")
        println("7. Logout")
        println("Please Choose Option From Above.... :-)")
      }
      def displayCustomerMenuOption() {
       // logger.info("Customer Entered")
        println("Welcome To Car Rental System...!")
        println("====================================")
        println("1. Search For Car")
        println("2. Book a Car")
        println("3. Issue Payment")
        println("4. Logout")
        println("Please Choose Option From Above.... :-)")
      }
      println("Enter Email Id:")
      var customerEmailInput=""
      customerEmailInput= StdIn.readLine.toString
      val adminID="admin@gmail.com"
      //logger.info(s"${adminID} Entering")
      //---------------------------------------------------get user name-----------------
      var custname=""
      val source = Source.fromFile(userfileName)
      for (line <- source.getLines())
        {
          if(line.contains(customerEmailInput)){
              val lineSplit= line.toString.split(",").toList
              var temp=lineSplit(1)
              //print(temp)
          }

        }
      //---------------------exit----------------------------
      var choiceCnt = 0
      var userOption=0

      // var Maps: Map[String, Int] = Map("cId" -> 0)
      if (customerEmailInput==adminID) {
        do {
          displayAdminMenuOption()
          choiceCnt = StdIn.readLine.toInt
          var op = new OptionMenu
          op.findOption(choiceCnt, carinfodata, carfileName)
        } while (choiceCnt != 7)
      }
      else if(custdata.contains(customerEmailInput)){
        do {
          displayCustomerMenuOption()
          userOption = StdIn.readLine.toInt
          var op = new OptionMenu
          op.findCustomerOption(customerEmailInput,custname,userOption,userinfoData, carinfodata,carfileName)
        }while(userOption!=4)


      }
      else{
        val newUser= new UserRegistration
        newUser.writeUserDataToFile(userfileName,customerEmailInput)
        do {
          displayCustomerMenuOption()
          userOption = StdIn.readLine.toInt
          val op = new OptionMenu
          //carinfodata,carfileName,userinfoData,userfileNam,custdata
          op.findCustomerOption(customerEmailInput,custname,userOption,userinfoData, carinfodata,carfileName)
        }while(userOption!=4)
      }
    }






    def main(args: Array[String]): Unit = {

      val carfileName = "CSV/car.csv"
      val userfileName = "CSV/User.csv"
      var carinfodata: List[List[String]] = List()
      var userinfoData: List[List[String]]= List()
      var csv=new CSVReader
      var customer= new UserRegistration
      carinfodata = csv.readCSV(carfileName)
      userinfoData= csv.readCSV(userfileName)
      var customerEmailId = userinfoData.map(r => r(0)).map(_.toString)
      // println(customerEmailId.contains("ashish@gmail.com"))
      displayMenu(carinfodata,carfileName,userinfoData,userfileName,customerEmailId)
    }


  }





