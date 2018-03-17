package model;

import java.util.Date;

/**
 * Created by Asif on 3/17/2018.
 */

public class ContributedItem {

    String trailstationid,learningtrailid,userid,fileurl,filedescription;
    Date timestamp;
    int itemid;
    public ContributedItem() {
    }

    public ContributedItem(String trailstationid, String learningtrailid, String userid, String fileurl, String filedescription, Date timestamp, int itemid) {
        this.trailstationid = trailstationid;
        this.learningtrailid = learningtrailid;
        this.userid = userid;
        this.fileurl = fileurl;
        this.filedescription = filedescription;
        this.timestamp = timestamp;
        this.itemid = itemid;
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

    public String getFileurl() {
        return fileurl;
    }

    public String getFiledescription() {
        return filedescription;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getItemid() {
        return itemid;
    }
}
