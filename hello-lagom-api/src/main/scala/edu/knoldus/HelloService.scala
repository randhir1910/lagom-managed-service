package edu.knoldus

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

case class ExternalUserData(id: Int, name: String, salary: Int)


object ExternalUserData {
  /**
    * Format for converting user messages to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[ExternalUserData] = Json.format[ExternalUserData]
}

trait HelloService extends Service {

  def addUser(): ServiceCall[ExternalUserData, String]

  def getUser(id: Int): ServiceCall[NotUsed, ExternalUserData]

  def updateUser(id: Int): ServiceCall[ExternalUserData, String]

  def deleteUser(id: Int): ServiceCall[NotUsed, Done]

  override final def descriptor: Descriptor = {
    import Service._
    // @formatter:off
    named("hello")
        .withCalls(
          restCall(Method.POST, "/addUser", addUser _),
          restCall(Method.GET, "/getUser/:id", getUser _),
          restCall(Method.PUT, "/updateUser/:id", updateUser _),
          restCall(Method.DELETE, "/deleteUser/:id", deleteUser _))
        .withAutoAcl(true)

  }

}
