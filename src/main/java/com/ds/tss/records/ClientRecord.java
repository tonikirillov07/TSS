package com.ds.tss.records;

import com.ds.tss.Constants;
import com.ds.tss.database.DatabaseConstants;
import com.ds.tss.database.DatabaseService;
import com.ds.tss.database.tablesConstants.Clients;
import com.ds.tss.dialogs.ErrorDialog;
import com.ds.tss.utils.settings.SettingsManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

import static com.ds.tss.Constants.CURRENT_DATABASE_FILE_KEY;

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
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY))).prepareStatement(select);
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

    public static @Nullable ClientRecord findClientRecordWithParameter(String row, String value){
        try {
            String select = "SELECT * FROM " + Clients.TABLE_NAME + " WHERE " + row + "='" + value + "'";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY))).prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();

            ClientRecord clientRecord = new ClientRecord(resultSet.getLong(DatabaseConstants.ID_ROW), Clients.TABLE_NAME, SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY), resultSet.getString(Clients.NAME_ROW),
                    resultSet.getString(Clients.CONTACTS_DATA_ROW), resultSet.getString(Clients.CAR_NAME_ROW), resultSet.getString(Clients.CAR_NUMBER_ROW));

            resultSet.close();
            preparedStatement.close();

            return clientRecord;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
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

    @Override
    public String toString() {
        return "ClientRecord{" +
                "name='" + name + '\'' +
                ", contacts='" + contacts + '\'' +
                ", carName='" + carName + '\'' +
                ", carNumber='" + carNumber + '\'' +
                '}';
    }
}
