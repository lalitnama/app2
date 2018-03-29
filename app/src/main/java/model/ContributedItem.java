package model;

/**
 * Created by Asif on 3/17/2018.
 */

public class ContributedItem {

    String trailStationId, learnTrailId, userid, fileUrl, discussion;
    String timestamp;
    String itemId;
    public ContributedItem() {
    }

    public ContributedItem(String trailstationid, String learningtrailid, String userid, String fileurl, String filedescription, String timestamp, String itemid) {
        this.trailStationId = trailstationid;
        this.learnTrailId = learningtrailid;
        this.userid = userid;
        this.fileUrl = fileurl;
        this.discussion = filedescription;
        this.timestamp = timestamp;
        this.itemId = itemid;
    }

    public String getTrailStationId() {
        return trailStationId;
    }

    public String getLearnTrailId() {
        return learnTrailId;
    }

    public String getUserid() {
        return userid;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getDiscussion() {
        return discussion;
    }

    public String getItemId() {
        return itemId;
    }
}
