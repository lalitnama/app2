package model;

/**
 * Created by Asif on 3/14/2018.
 */

public class TrailStation {
    String trailstationid;
    String trailid;
    String stationname;
    String instruction;
    String gps;

    public TrailStation(String trailstationid, String trailid, String stationname, String instruction, String gpsLocationString) {
        this.trailstationid = trailstationid;
        this.trailid = trailid;
        this.stationname = stationname;
        this.instruction = instruction;
        this.gps = gpsLocationString;
    }

    public TrailStation() {
    }

    public String getGPS() {
        return gps;
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
}
