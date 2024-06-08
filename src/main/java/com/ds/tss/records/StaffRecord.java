package com.ds.tss.records;

public class StaffRecord extends Record{
    private String name, post, branch, contacts, qualification;

    public StaffRecord(String tableName, String databasePath, String name, String post, String branch, String contacts, String qualification) {
        super(tableName, databasePath);
        this.name = name;
        this.post = post;
        this.branch = branch;
        this.contacts = contacts;
        this.qualification = qualification;
    }

    public StaffRecord(String tableName, String databasePath, long id, String name, String post, String branch, String contacts, String qualification) {
        super(tableName, databasePath, id);
        this.name = name;
        this.post = post;
        this.branch = branch;
        this.contacts = contacts;
        this.qualification = qualification;
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
}
