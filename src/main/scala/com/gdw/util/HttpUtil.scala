package com.gdw.util

import org.apache.http.client.methods.{HttpGet, HttpUriRequest}
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.apache.http.util.EntityUtils
import org.slf4j.LoggerFactory

/**
  * Created by bselvaraj on 11/5/17.
  */


case class Response(content: String)

object HttpUtil {
  val getHttpClient: CloseableHttpClient = HttpClientBuilder.create.build
  private val logger = LoggerFactory.getLogger(getClass)
  private val successCode = 200

  def get(uri: String): Response = {
    val request = new HttpGet(uri)
    processRequest(request)
  }

  private def processRequest(request: HttpUriRequest): Response = {
    val uri = request.getURI
    logger.info("URI " + uri)

    val resp = getHttpClient.execute(request)
    val statusLine = resp.getStatusLine
    val statusCode = statusLine.getStatusCode
    val content = EntityUtils.toString(resp.getEntity)
    if (statusCode == successCode) {
      logger.info(s"status code: $statusCode, status line: $statusLine")
      resp.close()
      Response(content)
    } else {
      logger.error(s"status code: $statusCode, status line: $statusLine, content: $content")
      resp.close()
      throw ApiCallException(s"URI ='$uri', statusLine: '$statusLine', content: '$content'")
    }
  }
}

case class ApiCallException(message: String) extends Exception
