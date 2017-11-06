package com.gdw.writer

/**
  * Created by bselvaraj
  */

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.SaveMode.{Overwrite => Replace}

object Writers {

  def write(df: DataFrame, savePath: String)(implicit writer: Writer) = {
    writer.write(df, savePath)
  }

  trait Writer {
    def write(df: DataFrame, savePath: String): Unit
  }

  object Writer {

    implicit val csvWriter = new Writer {
      def write(df: DataFrame, savePath: String): Unit = {
        df.repartition(1).write.mode(Replace).csv(savePath)
      }
    }

    implicit val jsonWriter = new Writer {
      def write(df: DataFrame, savePath: String): Unit = {
        df.repartition(1).write.mode(Replace).json(savePath)
      }
    }

    implicit val parquetWriter = new Writer {
      def write(df: DataFrame, savePath: String): Unit = {
        df.repartition(1).write.mode(Replace).parquet(savePath)
      }
    }

    implicit val orcWriter = new Writer {
      def write(df: DataFrame, savePath: String): Unit = {
        df.repartition(1).write.mode(Replace).orc(savePath)
      }
    }
  }

}
