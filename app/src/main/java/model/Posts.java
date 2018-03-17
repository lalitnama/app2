package model;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Asif on 3/17/2018.
 */

public class Posts {

    String trailstationid,learningtrailid,userid,post;
    Date timestamp;
    int postid;
    public Posts() {
    }

    public String getTrailStationID() {
        return trailstationid;
    }

    public void setTrailStationID(String trailstationid) {
        this.trailstationid = trailstationid;
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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getPostID() {
        return postid;
    }

    public void setPostID(int postid) {
        this.postid = postid;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
