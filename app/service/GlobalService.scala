package service
import java.sql._

import entity.zIssueTrackingIssueWorklogs

import org.joda.time.DateTime


/**
  * Created by vitaliys on 15.07.16.
  */
class GlobalService {
  val driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
  val url = "jdbc:jtds:sqlserver://216.121.48.194:9000/m247test"
  val login = "ITDimension"
  val password = "ITDimension$4968128$"
  DriverManager.registerDriver(new net.sourceforge.jtds.jdbc.Driver())
  val conn: Connection = DriverManager.getConnection(url, login, password)
  val format:DateTime = new DateTime()


  val getUserKeysQuery ="SELECT UserKey FROM zIssueTrackingIssueWorklogs GROUP BY userKey"
  val getWorkLogs = "SELECT * FROM zIssueTrackingIssueWorklogs WHERE UserKey = ? AND StartedDate " +
    "BETWEEN ? AND ? "
  val getTimeInHourFromCurrentYear = "SELECT  sum(TimeInSeconds)/3600 from zIssueTrackingIssueWorklogs where UserKey = ? " +
    "and StartedDate BETWEEN ? AND ? GROUP BY UserKey"

  /**
    * The method to get list UserKey's in selector
    * @return UserKey's values
    */
  def getUserKeys: List[String] = {
    try {
      val statement = conn.createStatement()
      val result = statement.executeQuery(getUserKeysQuery)
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
  def getWorkLogs(key:String, beginDate:String, endDate:String):List[zIssueTrackingIssueWorklogs] = {
    try {
      val statement = conn.prepareStatement(getWorkLogs)
      statement.setString(1, key)
      statement.setString(2,beginDate)
      statement.setString(3,endDate)
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

  /**
    * The method for calculating time work within a specified period
    * @param key get chosen key from selector
    * @param beginDate get begin date
    * @param endDate  get ending date
    * @return value of result calculating
    */
  def getTimeInHourFromCurrentYear(key:String, beginDate:String, endDate:String):Int ={
    try {
      val statement = conn.prepareStatement(getTimeInHourFromCurrentYear)
      statement.setString(1, key)
      statement.setString(2,beginDate)
      statement.setString(3,endDate)
      val result = statement.executeQuery()
      result.next()
      result.getInt(1)

  }catch {
      case e: SQLException => e.getNextException
        0
    }finally {closeConnection()}
}


  /**
    * Close connection from DB
    */
  def closeConnection(): Unit = {
    conn.close()
  }




}


