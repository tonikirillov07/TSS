package com.ds.tss.records;

import com.ds.tss.Constants;
import com.ds.tss.database.DatabaseService;
import com.ds.tss.database.tablesConstants.Services;
import com.ds.tss.dialogs.ErrorDialog;
import com.ds.tss.utils.settings.SettingsManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class ServicesRecord extends Record{
    private String name, description, duration;
    private double cost;

    public ServicesRecord(){
        super(null, null);
    }

    public ServicesRecord(String tableName, String databasePath, String name, String description, String duration, double cost) {
        super(tableName, databasePath);
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.cost = cost;
    }

    public ServicesRecord(String tableName, String databasePath, long id, String name, String description, String duration, double cost) {
        super(tableName, databasePath, id);
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.cost = cost;
    }

    public static boolean findServicesWithName(String name){
        try {
            String select = "SELECT * FROM " + Services.TABLE_NAME + " WHERE " + Services.NAME_ROW + "='" + name + "'";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean isThisServiceExists = resultSet.next();

            resultSet.close();
            preparedStatement.close();

            return isThisServiceExists;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
