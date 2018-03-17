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

    public LearningTrial(String learningtrailid, String userid, String traildate, String trailname, Date timestamp) {
        this.learningtrailid = learningtrailid;
        this.userid = userid;
        this.traildate = traildate;
        this.trailname = trailname;
        this.timestamp = timestamp;
    }

    public String getLearningtrailid() {
        return learningtrailid;
    }

    public String getUserid() {
        return userid;
    }

    public String getTraildate() {
        return traildate;
    }

    public String getTrailname() {
        return trailname;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
