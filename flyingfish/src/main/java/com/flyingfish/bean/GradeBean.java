package com.flyingfish.bean;

/**
 * Created by Administrator on 2017/2/7 0007.
 */
public class GradeBean {


    private String grade;
    private String ischeck;

    public GradeBean(String grade, String ischeck) {
        this.grade = grade;
        this.ischeck = ischeck;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }


}
