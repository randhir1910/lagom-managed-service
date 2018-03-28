package edu.knoldus

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import edu.knoldus.user.api.{UserData, UserService}
import play.api.Logger

import scala.concurrent.{ExecutionContext, Future}

class HelloServiceImpl(userService: UserService)(implicit ec: ExecutionContext) extends HelloService {

  override def getUser(id: Int): ServiceCall[NotUsed, ExternalUserData] = ServiceCall { _ =>

    val result = userService.getUser(id).invoke()
    result.map(response => ExternalUserData(response.id, response.name, response.salary))
  }

  override def addUser(): ServiceCall[ExternalUserData, String] = ServiceCall { request =>

    val user = UserData(request.id, request.name, request.salary)
    userService.createUser().invoke(user)
  }

  override def updateUser(id: Int): ServiceCall[ExternalUserData, String] = ServiceCall { request =>
    val user = UserData(request.id, request.name, request.salary)
    userService.updateUser(id).invoke(user)

  }

  override def deleteUser(id: Int): ServiceCall[NotUsed, Done] = ServiceCall { request =>
    userService.deleteUser(id).invoke()
  }
}