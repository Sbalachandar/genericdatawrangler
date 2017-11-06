package Impl

/**
  * Created by bselvaraj on 11/5/17.
  */

import com.gdw.reader.Readers._
import com.gdw.writer.Writers._

object WriterTesting{

  def main(args: Array[String]) {
   val readpath ="src/main/resources/sales.csv"
    val df= read(readpath)(Reader.csvReader)
    val writePath ="/tmp/csv/"
    df.show(1000, false)
    println("writing csv file ")

    write(df,"/tmp/csv/")(Writer.csvWriter)
    println("writing orc file ")

    write(df,"/tmp/orc/")(Writer.orcWriter)
    println("writing parquet file ")

    write(df,"/tmp/parquet/")(Writer.parquetWriter)

    println("writing json file ")
    write(df,"/tmp/json/")(Writer.jsonWriter)

    println("parquet reader ")
    read("/tmp/parquet/")(Reader.parquetReader).show(1000,false)

    println("orc reader ")
    read("/tmp/orc/")(Reader.orcReader).show(1000,false)

    println("json reader ")
    read("/tmp/json/")(Reader.jsonReader).show(1000,false)


  }

}
