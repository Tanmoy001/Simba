// src/main/scala/com/example/logging/ApiLogger.scala
package com.cimback.simba.logging

import java.time.LocalDateTime
import javax.sql.DataSource
import scala.util.Try

class ApiLogger(dataSource: DataSource) {

  def log(apiEndpoint: String, requestBody: String, responseStatus: Int, responseBody: String): Unit = {
    val connection = dataSource.getConnection()
    try {
      val statement = connection.prepareStatement(
        "INSERT INTO api_logs (timestamp, endpoint, request_body, response_status, response_body) VALUES (?, ?, ?, ?, ?)"
      )
      statement.setObject(1, LocalDateTime.now())
      statement.setString(2, apiEndpoint)
      statement.setString(3, requestBody)
      statement.setInt(4, responseStatus)
      statement.setString(5, responseBody)
      statement.executeUpdate()
    } finally {
      Try(connection.close()) // Ensure connection is closed
    }
  }
}