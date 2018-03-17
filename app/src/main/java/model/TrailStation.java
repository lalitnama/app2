package model;

/**
 * Created by Asif on 3/14/2018.
 */

public class TrailStation {

    String trailstationid,trailid,glocation,name,instruction;
    int sequenceid;
    public TrailStation() {
    }

    public TrailStation(String trailstationid, String trailid, String glocation, String name, String instruction, int sequenceid) {
        this.trailstationid = trailstationid;
        this.trailid = trailid;
        this.glocation = glocation;
        this.name = name;
        this.instruction = instruction;
        this.sequenceid = sequenceid;
    }

    public String getTrailstationid() {
        return trailstationid;
    }

    public String getTrailid() {
        return trailid;
    }

    public String getGlocation() {
        return glocation;
    }

    public String getName() {
        return name;
    }

    public String getInstruction() {
        return instruction;
    }

    public int getSequenceid() {
        return sequenceid;
    }
}
