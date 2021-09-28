package com.csvreader
import scala.io.Source

class CSVReader {
  def readCSV(filename: String): List[List[String]] = {
    var data: List[List[String]] = List()
    for (line <- Source.fromFile(filename).getLines) {

      var values = line.split(',').toList
      data = data :+ values
    }
    data
  }


}
