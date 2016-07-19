package controllers

import play.api.mvc._
import service.GlobalService


class Application extends Controller {


  /**
    * Getting key from selector on start page
    * @return value 'UserKey'
    */
  def getUserKeys = Action { implicit request =>
    val testService:GlobalService = new GlobalService
    Ok(views.html.startPage(testService.getUserKeys, "", List(), 0))
  }


  /**
    * Post main information about User - IssueId, Started time,
    * Comments, Working hours for a period of time
    * @return table with information about selected user
    */
  def getWorkLogs = Action { request =>
    val keyUser: String = request.body.asFormUrlEncoded.get("keyuser").head
    val beginDate:String = request.body.asFormUrlEncoded.get("begindate").head
    val endingDate:String = request.body.asFormUrlEncoded.get("endingdate").head
    val testService: GlobalService = new GlobalService
    Ok(views.html.startPage(testService.getUserKeys, keyUser,
      testService.getWorkLogs(keyUser,beginDate,endingDate),
      testService.getTimeInHourFromCurrentYear(keyUser,beginDate,endingDate)))
  }
}

