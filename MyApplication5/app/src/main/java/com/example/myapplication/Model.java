package com.example.myapplication;

public class Model {
    private String Sname;
    private  String dname;
    private long active;
    private long Recovered;
    private long Deceased;
    private long Confirmed;

    public Model(String sname, String dname, long active, long recovered, long deceased, long confirmed) {
        this.Sname = sname;
        this.dname = dname;
        this.active = active;
        this.Recovered = recovered;
        this.Deceased = deceased;
        this.Confirmed = confirmed;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        this.Sname = sname;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

    public long getRecovered() {
        return Recovered;
    }

    public void setRecovered(long recovered) {
        this.Recovered = recovered;
    }

    public long getDeceased() {
        return Deceased;
    }

    public void setDeceased(long deceased) {
        this.Deceased = deceased;
    }

    public long getConfirmed() {
        return Confirmed;
    }

    public void setConfirmed(long confirmed) {
        this.Confirmed = confirmed;
    }
}
