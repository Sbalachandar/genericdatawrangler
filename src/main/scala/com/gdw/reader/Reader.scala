package com.gdw.reader

import org.apache.spark.sql.DataFrame

/**
  * Created by bselvaraj on 11/5/17.
  */

import com.gdw.common.SparkEnv._

object Readers {

  def read[A] (input : String)(implicit reader: Reader[A]) = {
    reader.read(input)
  }

  trait Reader[A] {
    def read(inputPath : String) : A
  }

  object Reader {

    implicit val csvReader = new Reader[DataFrame] {
      def read(path :String) : DataFrame = {
        spark.read.option("header","true").csv(path)
      }
    }

    implicit  val jsonReader = new Reader[DataFrame] {
      def read(path : String) : DataFrame = {
        spark.read.json(path)
      }
    }

    implicit val parquetReader = new Reader[DataFrame] {
      def read(path : String) : DataFrame = {
        spark.read.parquet(path)
      }
    }


    implicit val orcReader = new Reader[DataFrame] {
      def read(path : String) : DataFrame = {
        spark.read.orc(path)
      }
    }
  }

}
