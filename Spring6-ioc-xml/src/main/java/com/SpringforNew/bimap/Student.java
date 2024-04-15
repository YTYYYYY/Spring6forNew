package com.SpringforNew.bimap;

import com.SpringforNew.bitest.Emp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Student {

    private String sname;
    private String sid;
    private Map<String,Teacher> teacherMap;
    private List<Course> courseList;

    public void info(){
        System.out.println("学生id："+sid+",姓名："+sname);
        System.out.println(teacherMap);
        System.out.println(courseList);
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public Map<String, Teacher> getTeacherMap() {
        return teacherMap;
    }

    public void setTeacherMap(Map<String, Teacher> teacherMap) {
        this.teacherMap = teacherMap;
    }


    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean-biref.xml");
        Student stu = context.getBean("stu", Student.class);
        stu.info();
    }


}
