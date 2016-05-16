package com.burntech.kyler.lean;

/**
 * Created by Kyler on 5/14/2015.
 */
public class Weight {

    private String weight;
    private String datetime;

    public Weight(String weight, String datetime) {
        this.weight = weight + " lb";
        this.datetime = datetime;
    }

    public String getWeight() {
        return this.weight;
    }

    public String getDatetime() {
        return this.datetime;
    }
}
