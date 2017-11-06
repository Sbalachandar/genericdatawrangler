package com.gdw.tranformer

/**
  * Created by bselvaraj on 11/5/17.
  */

object Transformers {

  trait Transformer {
    def transform[A](f: A => A): A
  }

}
