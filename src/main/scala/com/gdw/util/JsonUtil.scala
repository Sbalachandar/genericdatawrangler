package com.gdw.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.datatype.joda.JodaModule

import scala.reflect.Manifest

/**
  * Created by bselvaraj on 11/5/17.
  */

object JsonUtil {

  val mapper = new ObjectMapper with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.registerModule(new JodaModule)
  mapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def toJson(value: Map[Symbol, Any]): String = {
    toJson(value map { case (k,v) => k.name -> v})
  }

  def toJson(value: Any): String = {
    mapper.writeValueAsString(value)
  }

  def toMap(json: String): Map[String, Any] = fromJson[Map[String,Any]](json)

  def toMapOf[V](json:String)(implicit m: Manifest[V]): Map[String, V] = fromJson[Map[String,V]](json)

  def fromJson[T: Manifest](json: String): T = {
    mapper.readValue[T](json)
  }

  def toPrettyJson(obj: Any): String = mapper.writerWithDefaultPrettyPrinter.writeValueAsString(obj)
}


object JsonImplicits {

  implicit class Serializer(unMarshallMe: String) {
    def toMapOf[V: Manifest]: Map[String,V] = JsonUtil.toMapOf[V](unMarshallMe)
    def fromJson[T: Manifest]: T =  JsonUtil.fromJson[T](unMarshallMe)
  }

  implicit class Deserializer[T](marshallMe: T) {
    def toJson: String = JsonUtil.toJson(marshallMe)
    def toPrettyJson: String = JsonUtil.toPrettyJson(marshallMe)
  }
}