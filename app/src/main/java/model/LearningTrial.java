package model;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Asif on 3/14/2018.
 */

public class LearningTrial {

    String learningtrailid,userid,traildate,trailname;
    Date timestamp;
    public LearningTrial() {
    }

    public String getLearningTrailID() {
        return learningtrailid;
    }

    public void setLearningTrailID(String learningtrailid) {
        this.learningtrailid = learningtrailid;
    }

    public String getUserID() {
        return userid;
    }

    public void setUserID(String userid) {
        this.userid = userid;
    }

    public String getTrailDate() {
        return traildate;
    }

    public void setTrailDate(String traildate) {
        this.traildate = traildate;
    }

    public String getTrailName() {
        return trailname;
    }

    public void setTrailName(String trailname) {
        this.trailname = trailname;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
