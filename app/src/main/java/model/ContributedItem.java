package model;

/**
 * Created by Asif on 3/17/2018.
 */

public class ContributedItem {

    String trailStationId;
    String learnTrailId;
    String userid;
    String fileUrl;


    String title;
    String discussion;
    String timestamp;
    String itemId;
    public ContributedItem() {
    }

    public ContributedItem(String trailstationid, String learningtrailid, String userid, String fileurl, String title, String filedescription, String timestamp, String itemid) {
        this.trailStationId = trailstationid;
        this.learnTrailId = learningtrailid;
        this.userid = userid;
        this.fileUrl = fileurl;
        this.title = title;
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



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
