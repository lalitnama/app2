package model;

/**
 * Created by Asif on 3/14/2018.
 */

public class TrailStation {

    String trailstationid,trailid,glocation,name,instruction;
    int sequenceid;
    public TrailStation() {
    }

    public String getTrailStationID() {
        return trailstationid;
    }

    public void setTrailStationID(String trailstationid) {
        this.trailstationid = trailstationid;
    }

    public String getTrailID() {
        return trailid;
    }

    public void setTrailID(String trailid) {
        this.trailid = trailid;
    }

    public String getGeoLocation() {
        return glocation;
    }

    public void setGeoLocation(String glocation) {
        this.glocation = glocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getSquenceID() {
        return sequenceid;
    }

    public void setSquenceID(int sequenceid) {
        this.sequenceid = sequenceid;
    }

}
