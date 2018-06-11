package com.ckern.zentralrechner.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Version {

    @Id
    private String id;

    private String latestTimestamp;

    private ArrayList<Windpark> windparks;

    public Version(String id) {
        this.id = id;
        this.windparks = new ArrayList<>();
    }

    public void addVersion(Windpark windpark) {
        this.windparks.add(windpark);
        this.latestTimestamp = windpark.getTimeStamp();
    }

    public String getId() {
        return id;
    }

    public String getLatestTimestamp() {
        return latestTimestamp;
    }

    public ArrayList<Windpark> getVersions() {
        return windparks;
    }

    public Windpark getLatestWindpark() {
        for (Windpark windpark : this.windparks) {
            if (windpark.getTimeStamp().equals(this.latestTimestamp)) {
                return windpark;
            }
        }
        return null;
    }
}
