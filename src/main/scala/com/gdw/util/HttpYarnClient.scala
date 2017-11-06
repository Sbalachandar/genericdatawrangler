package com.gdw.util

import java.net.URI

case class YarnAppInstance(id: String, name: String, user: String, state: String, trackingUrl: String)

import play.api.libs.json.Json

/**
  * Created by bselvaraj on 11/5/17.
  */

case class HttpYarnClient(url: String) {

  private val basePath = "/ws/v1/cluster/"
  private val endPoint = new URI(url + basePath).normalize().toString


  import com.gdw.util.JsonImplicits._

  def getApps(userName: String, states: Set[String]): Set[YarnAppInstance] = {
    val uri = endPoint + s"""apps?user=$userName&states=${states.mkString(",")}"""
    val resp = HttpUtil.get(uri).content
    val yarnApps = (Json.parse(resp) \ "apps" \ "app").toOption
    yarnApps match {
      case Some(apps) => Json.stringify(apps).fromJson[Set[YarnAppInstance]]
      case None => Set.empty
    }
  }
}

object YarnClient {
  def apply(url: String): HttpYarnClient = HttpYarnClient(url)
}
