package com.ds.tss.records;

import com.ds.tss.Constants;
import com.ds.tss.database.DatabaseService;
import com.ds.tss.database.tablesConstants.Branches;
import com.ds.tss.dialogs.ErrorDialog;
import com.ds.tss.utils.settings.SettingsManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class BranchRecord extends Record{
    private String name, address, telephone, workClocks, manager;

    public BranchRecord(){
        super(null, null);
    }

    public BranchRecord(String tableName, String databasePath, String name, String address, String telephone, String workClocks, String manager) {
        super(tableName, databasePath);
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.workClocks = workClocks;
        this.manager = manager;
    }

    public BranchRecord(String tableName, String databasePath, long id, String name, String address, String telephone, String workClocks, String manager) {
        super(tableName, databasePath, id);
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.workClocks = workClocks;
        this.manager = manager;
    }

    public static boolean findBranchWithName(String name){
        try {
            String select = "SELECT * FROM " + Branches.TABLE_NAME + " WHERE " + Branches.NAME_ROW + "='" + name + "'";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean isThisBranchExists = resultSet.next();

            resultSet.close();
            preparedStatement.close();

            return isThisBranchExists;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWorkClocks() {
        return workClocks;
    }

    public void setWorkClocks(String workClocks) {
        this.workClocks = workClocks;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
