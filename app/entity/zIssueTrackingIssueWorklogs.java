package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * Created by vitaliys on 13.07.16.
 */

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@DynamicUpdate
public class zIssueTrackingIssueWorklogs {

    private Integer TargetId;
    private Integer WorklogId;
    private String IssueId;
    private DateTime CreatedTime;
    private DateTime    StartedTime;
    private DateTime UpdateTime;
    private Integer TimeInSeconds;
    private String UserKey;

    @GeneratedValue
    @Id
    @JsonIgnore
    public Integer getTargetId() {
        return TargetId;
    }

    public void setTargetId(Integer targetId) {
        TargetId = targetId;
    }
    @JsonIgnore
    public Integer getWorklogId() {
        return WorklogId;
    }

    public void setWorklogId(Integer worklogId) {
        WorklogId = worklogId;
    }
    @JsonIgnore
    public String getIssueId() {
        return IssueId;
    }

    public void setIssueId(String issueId) {
        IssueId = issueId;
    }
    @JsonIgnore
    public DateTime getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(DateTime createdTime) {
        CreatedTime = createdTime;
    }
    @JsonIgnore
    public DateTime getStartedTime() {
        return StartedTime;
    }

    public void setStartedTime(DateTime startedTime) {
        StartedTime = startedTime;
    }
    @JsonIgnore
    public DateTime getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(DateTime updateTime) {
        UpdateTime = updateTime;
    }
    @JsonIgnore
    public Integer getTimeInSeconds() {
        return TimeInSeconds;
    }

    public void setTimeInSeconds(Integer timeInSeconds) {
        TimeInSeconds = timeInSeconds;
    }
    @JsonIgnore
    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }
}



