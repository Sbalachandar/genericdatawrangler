package com.gdw.tranformer

/**
  * Created by bselvaraj on 11/5/17.
  */

import com.gdw.common.SparkEnv._
import org.apache.spark.sql.DataFrame

object Transformers {

    trait Transformer {
      def transform [A] ( f : A => A)  :A
    }

}
