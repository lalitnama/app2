package model;

/**
 * Created by Asif on 3/14/2018.
 */

public class TrailStation {
    String trailstationid;
    String trailid;
    String stationname;
    String instruction;
    double longitude;
    double latitude;

    public TrailStation(String trailstationid, String trailid, String stationname, String instruction, double latitude, double longitude) {
        this.trailstationid = trailstationid;
        this.trailid = trailid;
        this.stationname = stationname;
        this.instruction = instruction;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public TrailStation() {
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
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
