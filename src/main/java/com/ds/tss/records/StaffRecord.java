package com.ds.tss.records;

public class StaffRecord extends Record{
    private String name, post, branch, contacts, qualification;
    private double salary;

    public StaffRecord(String tableName, String databasePath, String name, String post, String branch, String contacts, String qualification, double salary) {
        super(tableName, databasePath);
        this.name = name;
        this.post = post;
        this.branch = branch;
        this.contacts = contacts;
        this.qualification = qualification;
        this.salary = salary;
    }

    public StaffRecord(String tableName, String databasePath, long id, String name, String post, String branch, String contacts, String qualification, double salary) {
        super(tableName, databasePath, id);
        this.name = name;
        this.post = post;
        this.branch = branch;
        this.contacts = contacts;
        this.qualification = qualification;
        this.salary = salary;
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
