package service
import java.sql._
import java.util.Calendar

import entity.zIssueTrackingIssueWorklogs
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
/**
  * Created by vitaliys on 15.07.16.
  */
class GlobalService {
  DriverManager.registerDriver(new net.sourceforge.jtds.jdbc.Driver())
  val conn: Connection = DriverManager.getConnection(GlobalService.url, GlobalService.login, GlobalService.password)

  /**
    * The method to get list UserKey's in selector
    * @return UserKey's values
    */
  def getUserKeys: List[String] = {
    try {
      val statement = conn.createStatement()
      val result = statement.executeQuery(GlobalService.GET_USER_KEYS_QUERY)
      val resultIter = new Iterator[String] {
        def hasNext = result.next()
        def next() = result.getString("UserKey")
      }
      val resultList = resultIter.toList
      resultList
    } catch {
      case e:SQLException => e.getNextException
        List()
    }
  }


  /**
    * Global method to work with Database. Getting all information from DB and posting on the page.
    * @param key get chosen key from selector
    * @param beginDate get begin date
    * @param endDate  get ending date
    * @return list of chosen values
    */
  def getWorkLogs(key:String, beginDate:String, endingDate:String):List[zIssueTrackingIssueWorklogs] = {
    try {
      val statement = conn.prepareStatement(GlobalService.GET_WORK_LOGS)
      statement.setString(1, key)
      statement.setString(2,beginDate)
      statement.setString(3,endingDate)
      val result = statement.executeQuery()
      val resultIter = new Iterator[zIssueTrackingIssueWorklogs] {
        def hasNext = result.next()
        def next():zIssueTrackingIssueWorklogs = {
          val allColumns:zIssueTrackingIssueWorklogs = new zIssueTrackingIssueWorklogs()
          allColumns.setTimeInSeconds(result.getInt("TimeInSeconds"))
          allColumns.setTargetId(result.getInt("TargetId"))
          allColumns.setWorklogId(result.getInt("WorklogId"))
          allColumns.setIssueId(result.getString("IssueId"))
          allColumns.setUserKey(result.getString("UserKey"))
          allColumns.setCreatedDate(new DateTime(result.getTimestamp("CreatedDate")))
          allColumns.setUpdatedDate(new DateTime(result.getTimestamp("UpdatedDate")))
          allColumns.setStartedDate(new DateTime(result.getTimestamp("StartedDate")))
          allColumns.setComment(result.getString("Comment"))
          allColumns
        }
      }
      val resultList = resultIter.toList
      resultList
    }catch {
      case e:SQLException => e.getNextException
        List()
    }
  }


//  /**
//    * The method for calculating time work within a specified period
//    * @param key get chosen key from selector
//    * @param beginDate get begin date
//    * @param endingDate  get ending date
//    * @return value of result calculating
//    */
//  def getTimeInHourFromCurrentYear(key:String, beginDate:String, endingDate:String):Int ={
//    try {
//      val statement = conn.prepareStatement(GlobalService.GET_TIME_IN_HOUR_FROM_CURR_YEAR)
//      statement.setString(1,key)
//      statement.setString(2,beginDate)
//      statement.setString(3,endingDate)
//      val result = statement.executeQuery()
//      result.next()
//      result.getInt(1)
//    }catch {
//      case e: SQLException => e.getNextException
//        0
//    }finally {
//      closeConnection()
//    }
//}


  def getTimesForAllUsersFromSelectMonth(beginDate:String, endingDate:String): Map[String,Int] ={
    try{
      val statement = conn.prepareStatement(GlobalService.GET_TIME_FOR_ALL_USERS_FROM_SELECT_MONTH)
      statement.setString(1,beginDate)
      statement.setString(2,endingDate)
      val resultSet = statement.executeQuery()
      val resultIter = new Iterator[(String, Int)] {
        def hasNext = resultSet.next()
        def next() = {
          (resultSet.getString(1), resultSet.getInt(2))
        }
      }
      val resultMap = resultIter.toMap[String,Int]
      resultMap
    } catch {
      case e:SQLException => e.getNextException
        Map()
    }
    }
    /**
    * Close connection from DB
    */
  def closeConnection(): Unit = {
    conn.close()
  }

  def getBeginDate:String={
   Calendar.getInstance().get(Calendar.YEAR).toString+"-"+
      String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1)+"-01"
    }


  def getEndDate:String={
    GlobalService.formatter.print(new DateTime())
  }
}

 object GlobalService {
   val driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
   val url = "jdbc:jtds:sqlserver://216.121.48.194:9000/m247test"
   val login = "ITDimension"
   val password = "ITDimension$4968128$"


   val GET_USER_KEYS_QUERY ="SELECT UserKey FROM zIssueTrackingIssueWorklogs GROUP BY userKey"
   val GET_WORK_LOGS = "SELECT * FROM zIssueTrackingIssueWorklogs WHERE UserKey = ? and TargetId LIKE '270' AND StartedDate " +
     "BETWEEN ? AND ? "
//   val GET_TIME_IN_HOUR_FROM_CURR_YEAR = "SELECT  sum(TimeInSeconds) from zIssueTrackingIssueWorklogs where UserKey = ? " +
//     "AND TargetId LIKE '270' and StartedDate BETWEEN ? AND ? GROUP BY UserKey "
   val GET_TIME_FOR_ALL_USERS_FROM_SELECT_MONTH = "SELECT U.DisplayName, SUM(TimeInSeconds/3600) AS 'Hours' FROM zIssueTrackingIssueWorklogs W, " +
     "zIssueTrackingUsers U WHERE W.TargetId = 270 AND W.TargetId = U.TargetId AND W.UserKey  = U.UserKey    " +
     " AND StartedDate BETWEEN ? AND ? GROUP BY U.DisplayName ORDER BY Hours DESC"
   val START_OPTION = "All"

   val formatter:DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd")

   def timeHour(t:Int) ={
     t/3600
   }
   def timeMin(t:Int) ={
     t%3600/60
   }

 }
