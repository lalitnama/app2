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

    public Posts(String trailstationid, String learningtrailid, String userid, String post, Date timestamp, int postid) {
        this.trailstationid = trailstationid;
        this.learningtrailid = learningtrailid;
        this.userid = userid;
        this.post = post;
        this.timestamp = timestamp;
        this.postid = postid;
    }

    public String getTrailstationid() {
        return trailstationid;
    }

    public String getLearningtrailid() {
        return learningtrailid;
    }

    public String getUserid() {
        return userid;
    }

    public String getPost() {
        return post;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getPostid() {
        return postid;
    }
}
