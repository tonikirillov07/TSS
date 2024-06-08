package com.ds.tss.records;

public class MaintainingRecord extends Record{
    private String date, client, auto, service, branch, mechanic;

    public MaintainingRecord(String tableName, String databasePath, String date, String client, String auto, String service, String branch, String mechanic) {
        super(tableName, databasePath);
        this.date = date;
        this.client = client;
        this.auto = auto;
        this.service = service;
        this.branch = branch;
        this.mechanic = mechanic;
    }

    public MaintainingRecord(String tableName, String databasePath, long id, String date, String client, String auto, String service, String branch, String mechanic) {
        super(tableName, databasePath, id);
        this.date = date;
        this.client = client;
        this.auto = auto;
        this.service = service;
        this.branch = branch;
        this.mechanic = mechanic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getMechanic() {
        return mechanic;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }
}
