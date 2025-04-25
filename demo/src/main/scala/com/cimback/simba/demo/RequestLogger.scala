// src/main/scala/com/cimback/simba/logging/RequestLogger.scala
package com.cimback.simba

import java.sql.{Connection, DriverManager, PreparedStatement}
import scala.util.{Try, Using}

class RequestLogger(dbUrl: String, dbUser: String = "postgres", dbPassword: String = "postgres") {
  private val connection: Connection = {
    Class.forName("org.postgresql.Driver")
    DriverManager.getConnection(dbUrl, dbUser, dbPassword)
  }

  def log(
           endpoint: String,
           userId: Option[Int],
           params: Map[String, String]
         ): Try[Unit] = Using.Manager { use =>
    val stmt = use(connection.prepareStatement(
      """INSERT INTO request_history
        |(timestamp, endpoint, parameters, user_id)
        |VALUES (NOW(), ?, ?, ?)""".stripMargin))

    stmt.setString(1, endpoint)
    stmt.setString(2, params.mkString(", "))
    userId match {
      case Some(id) => stmt.setInt(3, id)
      case None => stmt.setNull(3, java.sql.Types.INTEGER)
    }
    stmt.executeUpdate()
  }

  def close(): Unit = connection.close()
}