package controllers

import java.util.Calendar

import entity.zIssueTrackingIssueWorklogs
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import play.api.mvc._
import service.GlobalService


class Application extends Controller {
  /**
    * Getting key from selector on start page
    * @return value 'UserKey'
    */
  def getUserKeys = Action { implicit request =>
    val globalService = new GlobalService()
    Redirect(s"/getlogs/${GlobalService.START_OPTION}?beginDate="+globalService.getBeginDate+"&endDate="+globalService.getEndDate)
  }
  /**
    * Post main information about User - IssueId, Started time,
    * Comments, Working hours for a period of time
    * @return table with information about selected user
    */
    def getWorkLogs(userkey:String,beginDate:String,endDate:String) = Action { request =>
    val globalService: GlobalService = new GlobalService
    if(GlobalService.START_OPTION.equals(userkey)|userkey==null) {
      Ok(views.html.startingPage(
        //all
        userKeys = globalService.getUserKeys,
        selectedKey = userkey,
        beginDate=beginDate,
        endDate=endDate,
        startTable=globalService.getTimesForAllUsersFromSelectMonth(beginDate,endDate)))
      } else {
        //user
        val logs : List[zIssueTrackingIssueWorklogs] = globalService.getWorkLogs(userkey, beginDate, endDate)
        val sumTime:Int=logs.foldLeft(0)(_+_.getTimeInSeconds)
        Ok(views.html.startingPage(
          userKeys=globalService.getUserKeys,
          selectedKey=userkey,
          workLogs = logs,
          time = sumTime,
          beginDate=beginDate,
          endDate=endDate))
      }

    }
}

