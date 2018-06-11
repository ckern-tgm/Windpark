package com.ckern.zentralrechner.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Windpark {


    private String id;

    @Id
    private String timeStamp;

    private List<Windrad> windraeder;

    public Windpark(String id, String timeStamp) {
        this.id = id;
        this.windraeder = new ArrayList<>();
        this.timeStamp = timeStamp;
    }

    public void addWindrad(Windrad windrad) {
        this.windraeder.add(windrad);
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getId() {
        return id;
    }

    public List<Windrad> getWindraeder() {
        return windraeder;
    }

}
