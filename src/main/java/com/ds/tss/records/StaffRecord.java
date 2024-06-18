package com.ds.tss.records;

import com.ds.tss.Constants;

public class StaffRecord extends Record{
    private String name, post, branch, contacts, qualification;
    private double salary, workedTime, awards;
    private int passedWorks;

    public StaffRecord(String tableName, String databasePath, String name, String post, String branch, String contacts, String qualification, double workedTime, double awards, int passedWorks, double salary) {
        super(tableName, databasePath);
        this.name = name;
        this.post = post;
        this.branch = branch;
        this.contacts = contacts;
        this.qualification = qualification;
        this.salary = salary;
        this.workedTime = workedTime;
        this.awards = awards;
        this.passedWorks = passedWorks;
    }

    public StaffRecord(String tableName, String databasePath, long id, String name, String post, String branch, String contacts, String qualification,  double workedTime, double awards, int passedWorks, double salary) {
        super(tableName, databasePath, id);
        this.name = name;
        this.post = post;
        this.branch = branch;
        this.contacts = contacts;
        this.qualification = qualification;
        this.salary = salary;
        this.workedTime = workedTime;
        this.awards = awards;
        this.passedWorks = passedWorks;
    }

    public static double calculateSalary(double workedTime, double awards, int passedWorks){
        return (workedTime * Constants.ONE_WORKED_HOUR_COST) + awards + (passedWorks * Constants.ONE_PASSED_WORK_COST);
    }

    public double getWorkedTime() {
        return workedTime;
    }

    public void setWorkedTime(double workedTime) {
        this.workedTime = workedTime;
    }

    public double getAwards() {
        return awards;
    }

    public void setAwards(double awards) {
        this.awards = awards;
    }

    public int getPassedWorks() {
        return passedWorks;
    }

    public void setPassedWorks(int passedWorks) {
        this.passedWorks = passedWorks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
