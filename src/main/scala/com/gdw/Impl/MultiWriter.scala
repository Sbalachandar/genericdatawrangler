package com.gdw.impl

/**
  * Created by bselvaraj on 11/5/17.
  */

import com.gdw.common.SparkEnv._
import com.gdw.reader.Readers._
import com.gdw.writer.Writers._
import org.apache.spark.sql.DataFrame


object MultiWriters {

  val defaultPath = "src/main/resources/sales.csv"
  val writers: List[WritePath] =
    List(WritePath.apply(Writer.csvWriter, "/tmp/csv/salesresult/"),
      WritePath.apply(Writer.jsonWriter, "/tmp/json/salesresult/"),
      WritePath.apply(Writer.parquetWriter, "/tmp/parquet/salesresult/"),
      WritePath.apply(Writer.orcWriter, "/tmp/csv/salesresult/"))

  def writeDataAsync(data: DataFrame, writers: List[WritePath] = writers) = {
    writers.par.foreach(
      w => w.writer.write(data, w.outputPath)
    )
  }

  def main(args: Array[String]) {

    writeData(readSalesData())

    /*
     more /tmp/json/salesresult/.json
    {"transactionId":"112","customerId":"2","itemId":"2","amountPaid":"505.0"}
    {"transactionId":"113","customerId":"3","itemId":"3","amountPaid":"510.0"}
    {"transactionId":"114","customerId":"4","itemId":"4","amountPaid":"600.0"}
   */

  }

  def readSalesData(path: String = defaultPath): DataFrame = {
    val df = read(path)(Reader.csvReader)
    //Transform the data by running sql on dataframe
    df.createOrReplaceTempView("sales")
    spark.sql("select * from sales where amountPaid > 500").toDF()
  }

  def writeData(data: DataFrame, writers: List[WritePath] = writers) = {
    writers.foreach(
      w => w.writer.write(data, w.outputPath)
    )
  }

  case class WritePath(writer: Writer, outputPath: String)

}
