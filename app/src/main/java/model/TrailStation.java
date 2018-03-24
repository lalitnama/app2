package model;

/**
 * Created by Asif on 3/14/2018.
 */

public class TrailStation {
    String trailstationid;
    String trailid;
    String glocation;
    String stationname;
    String instruction;
    int sequenceid;

    public TrailStation(String trailstationid, String trailid, String glocation, String stationname, String instruction, int sequenceid) {
        this.trailstationid = trailstationid;
        this.trailid = trailid;
        this.glocation = glocation;
        this.stationname = stationname;
        this.instruction = instruction;
        this.sequenceid = sequenceid;
    }


    public TrailStation() {
    }

    public String getTrailstationid() {
        return trailstationid;
    }

    public void setTrailstationid(String trailstationid) {
        this.trailstationid = trailstationid;
    }

    public String getTrailid() {
        return trailid;
    }

    public void setTrailid(String trailid) {
        this.trailid = trailid;
    }

    public String getGlocation() {
        return glocation;
    }

    public void setGlocation(String glocation) {
        this.glocation = glocation;
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getSequenceid() {
        return sequenceid;
    }

    public void setSequenceid(int sequenceid) {
        this.sequenceid = sequenceid;
    }




}
