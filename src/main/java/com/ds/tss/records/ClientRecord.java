package com.ds.tss.records;

import com.ds.tss.Constants;
import com.ds.tss.database.DatabaseService;
import com.ds.tss.database.tablesConstants.Clients;
import com.ds.tss.dialogs.ErrorDialog;
import com.ds.tss.utils.settings.SettingsManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class ClientRecord extends Record {
    private String name, contacts, carName, carNumber;

    public ClientRecord(){
        super(null, null);
    }

    public ClientRecord(String tableName, String databasePath, String name, String contacts, String carName, String carNumber) {
        super(tableName, databasePath);
        this.name = name;
        this.contacts = contacts;
        this.carName = carName;
        this.carNumber = carNumber;
    }

    public ClientRecord(long id, String tableName, String databasePath, String name, String contacts, String carName, String carNumber) {
        super(tableName, databasePath, id);
        this.name = name;
        this.contacts = contacts;
        this.carName = carName;
        this.carNumber = carNumber;
    }

    public static boolean findClientWithParameter(String row, String value){
        try {
            String select = "SELECT * FROM " + Clients.TABLE_NAME + " WHERE " + row + "='" + value + "'";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean isThisAgentExists = resultSet.next();

            resultSet.close();
            preparedStatement.close();

            return isThisAgentExists;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
