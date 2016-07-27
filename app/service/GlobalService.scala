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
    *
    * @return UserKey's values
    */
  def getUserKeys: Map[String,String] = {
    try {
      val statement = conn.createStatement()
      val result = statement.executeQuery(GlobalService.GET_USER_KEYS_QUERY)
      val resultIter = new Iterator[(String,String)] {
        def hasNext = result.next()
        def next() = {
          (result.getString(1), result.getString(2))
        }
      }
      val resultMap = resultIter.toMap
      resultMap
    } catch {
      case e:SQLException => e.getErrorCode
        Map()
    }
  }


  /**
    * Global method to work with Database. Getting all information from DB and posting on the page.
    *
    * @param key get chosen key from selector
    * @param beginDate get begin date
    * @param endingDate  get ending date
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
      case e:SQLException => e.getErrorCode
        List()
    }
  }

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
      case e:SQLException => e.getErrorCode
        Map()
    }
  }

    def totalSum(beginDate:String, endingDate:String):Int={
    val x =  getTimesForAllUsersFromSelectMonth(beginDate,endingDate)
    x.values.sum
    }


    /**
    * Close connection from DB
    */
  def closeConnection(): Unit = {
    conn.close()
  }

  def getBeginDate:String={
   Calendar.getInstance().get(Calendar.YEAR).toString+"-0"+
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

   val GET_USER_KEYS_QUERY = "select U.DisplayName, U.UserKey from zIssueTrackingUsers U where U.TargetId=270 and U.UserKey in " +
     "(  Select W.UserKey from zIssueTrackingIssueWorklogs W where W.TargetId=270);"


   val GET_WORK_LOGS = "SELECT * FROM zIssueTrackingIssueWorklogs WHERE UserKey = ? and TargetId LIKE '270' AND StartedDate " +
     "BETWEEN ? AND ? "

   val GET_WORK_LOGS1 = "SELECT  U.DisplayName, W.* from zIssueTrackingIssueWorklogs W , zIssueTrackingUsers U where WHERE U.DisplayName = ? " +
     "W.StartedDate BETWEEN ? AND ? and W.TargetId like '270' AND W.TargetId = U.TargetId AND W.UserKey  = U.UserKey;"

   val GET_TIME_FOR_ALL_USERS_FROM_SELECT_MONTH = "SELECT U.DisplayName, SUM (TimeInSeconds) AS 'Hours' FROM zIssueTrackingIssueWorklogs W, " +
     "zIssueTrackingUsers U WHERE W.TargetId = 270 AND W.TargetId = U.TargetId AND W.UserKey  = U.UserKey    " +
     " AND StartedDate BETWEEN ? AND ? GROUP BY U.DisplayName ORDER BY Hours DESC"

   val START_OPTION = "All"

   val formatter: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd")

   def timeHour(t: Int) = {
     t / 3600
   }

   def timeMin(t: Int) = {
     t % 3600 / 60
   }


 }