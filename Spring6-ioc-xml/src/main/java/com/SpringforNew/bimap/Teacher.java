package com.SpringforNew.bimap;

public class Teacher {

    private String tname;
    private String tid;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "tname='" + tname + '\'' +
                ", tid='" + tid + '\'' +
                '}';
    }
}
