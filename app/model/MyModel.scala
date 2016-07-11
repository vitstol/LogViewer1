package model

import java.sql.ResultSet

import play.api.db
import play.api.db._


/**
  * Created by vitalys on 10.07.16.
  */
class MyModel {

  val connection = DB.getConnection()
  val statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
  val queryGetWorkLogs = "SELECT  CAST(ROUND(ISNULL(SUM(W.TimeInSeconds), 0) / 3600.0, 1) AS DECIMAL(8, 1)) AS 'TimeInHours' FROM zIssueTrackingIssueWorklogs W  WHERE W.TargetId = 270  AND(W.StartedDate >='{d1}' and W.StartedDate <='{d2}')"
  val queryGetTotalDevelopers = "SELECT  COUNT(DISTINCT(UserKey)) FROM zIssueTrackingIssueWorklogs W  WHERE W.TargetId = 270  AND(W.StartedDate >='{d1}' and W.StartedDate <='{d2}')"
  val queryGetFullInfo = "SELECT  W.UserKey AS 'Assignee',\tCAST(ROUND(ISNULL(SUM(W.TimeInSeconds),0)/3600.0,1) AS DECIMAL(8,1)) AS 'TimeInHours', COUNT(*) AS 'TimelogEntries' \tFROM zIssueTrackingIssueWorklogs W  WHERE W.TargetId = 270  AND(W.StartedDate >='{d1}' and W.StartedDate <='{d2}') group by W.UserKey\tORDER BY 'TimeInHours' DESC"



}
