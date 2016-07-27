package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@DynamicUpdate
public class zIssueTrackingIssueWorklogs {
    private Integer TargetId;
    private Integer WorklogId;
    private String IssueId;
    private DateTime CreatedDate;
    private DateTime StartedDate;
    private DateTime UpdatedDate;
    private Integer TimeInSeconds;
    private String  DisplayName;

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public Integer getTimeInSeconds() {
        return TimeInSeconds;
    }

    public void setTimeInSeconds(Integer timeInSeconds) {
        TimeInSeconds = timeInSeconds;
    }

    private String UserKey;
    private String Comment;

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

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

    public DateTime getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        CreatedDate = createdDate;
    }

    public DateTime getStartedDate() {
        return StartedDate;
    }

    public void setStartedDate(DateTime startedDate) {
        StartedDate = startedDate;
    }

    public DateTime getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(DateTime updatedDate) {
        UpdatedDate = updatedDate;
    }



    @JsonIgnore
    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public void formate(){

    }
}

