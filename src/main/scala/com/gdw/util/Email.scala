package com.gdw.util

import org.apache.commons.mail.{HtmlEmail, SimpleEmail}
import org.slf4j.LoggerFactory

/**
  * Created by bselvaraj on 11/5/17.
  */

object Email {

  private val logger = LoggerFactory.getLogger(getClass)

  def sendSimpleEmail(from: String, to: Seq[String], subject: String, body: String, host: String = "smtp.googlemail.com", port: Int = 465): Unit = {
    try {
      val email = new SimpleEmail
      email.setHostName(host)
      email.setSmtpPort(port)
      email.setFrom(from)
      email.addTo(to: _*)
      email.setSubject(subject)
      email.setMsg(body)
      email.send()
    } catch {
      case e: Exception => logger.error("Error occurred while sending email " + e.getStackTrace.mkString("\n"))
    }
  }

  def sendHtmlEmail(from: String, to: Seq[String], subject: String, body: String, host: String = "smtp.googlemail.com", port: Int = 465): Unit = {
    try {
      val email = new HtmlEmail
      email.setHostName(host)
      email.setSmtpPort(port)
      email.setFrom(from)
      email.addTo(to: _*)
      email.setSubject(subject)
      val htmlBody =
        s"""
           |<html>
           |<head>
           |<style>
           |table, th, td {
           |border: 1px solid black;
           |border-collapse: collapse;
           |}
           |</style>
           |</head>
           |<body>
           |$body
           |</body>
           |</html>
           |""".stripMargin
      email.setHtmlMsg(htmlBody)
      email.send()
    } catch {
      case e: Exception => logger.error("Error occurred while sending email " + e.getStackTrace.mkString("\n"))
    }

  }

  def getHtmlTable(data: Seq[Seq[Any]]): String = {
    s"""
       |<table>
       |<tr>${data.head.map(col => "<th>" + col + "</th>").mkString("")}</tr>
       |${data.tail.map(row => "<tr>" + row.map(col => "<td>" + col + "</td>").mkString("") + "</tr>").mkString("\n")}
       |</table>
       |""".stripMargin
  }

}


