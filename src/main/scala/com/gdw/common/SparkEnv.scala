package com.gdw.common

import org.apache.hadoop.fs.{FileContext, FileSystem}
import org.apache.spark.sql.SparkSession

/**
  * Created by bselvaraj
  */

object SparkEnv {

  val spark = SparkSession.builder.
    master("local")
    .appName("example")
    .getOrCreate()

  val hadoopFileSystem: FileSystem = FileSystem.get(spark.sparkContext.hadoopConfiguration)

  val hadoopFileContext: FileContext = FileContext.getFileContext(spark.sparkContext.hadoopConfiguration)

}
