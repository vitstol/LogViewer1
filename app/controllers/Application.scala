package controllers



import java.sql.Timestamp

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.JsonNode
import play.api.GlobalSettings
import play.api.libs.json.JsValue
import play.api.mvc._
import service.GlobalService


class Application extends Controller {
  def getUserKeys = Action { implicit request =>
    val testService:GlobalService = new GlobalService
    Ok(views.html.startPage(testService.getUserKeys, "", List(), 0))
  }

  def getWorkLogs = Action { request =>
    val keyUser: String = request.body.asFormUrlEncoded.get("keyuser").head
    val beginDate:String = request.body.asFormUrlEncoded.get("begindate").head
    val endingDate:String = request.body.asFormUrlEncoded.get("endingdate").head
    val testService: GlobalService = new GlobalService
    Ok(views.html.startPage(testService.getUserKeys, (keyUser),
      testService.getWorkLogs(keyUser,beginDate,endingDate),
      testService.getTimeInHourFromCurrentYear(keyUser,beginDate,endingDate)))
  }


}

