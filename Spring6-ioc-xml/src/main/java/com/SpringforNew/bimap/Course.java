package com.SpringforNew.bimap;

public class Course {

    private String cno;

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    @Override
    public String toString() {
        return "Course{" +
                "cno='" + cno + '\'' +
                '}';
    }
}
