package com.ds.tss.records;

import com.ds.tss.Constants;
import com.ds.tss.database.DatabaseService;
import com.ds.tss.database.tablesConstants.*;
import com.ds.tss.dialogs.ErrorDialog;
import com.ds.tss.utils.settings.SettingsManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ds.tss.database.DatabaseConstants.ID_ROW;

public class RecordsGetter {
    @Contract(pure = true)
    private static @NotNull String getSelectRequest(String tableName){
        return "SELECT * FROM " + tableName + " ORDER BY " + ID_ROW + " ASC";
    }

    public static @Nullable List<BranchRecord> getAllBranchesRecords(){
        try {
            String selectAll = getSelectRequest(Branches.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<BranchRecord> branchesRecords = new ArrayList<>();
            while (resultSet.next()){
                branchesRecords.add(new BranchRecord(Branches.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), resultSet.getLong(ID_ROW), resultSet.getString(Branches.NAME_ROW), resultSet.getString(Branches.ADDRESS_ROW),
                        resultSet.getString(Branches.TELEPHONE_ROW), resultSet.getString(Branches.WORK_CLOCKS_ROW), resultSet.getString(Branches.MANAGER_ROW)));
            }

            return branchesRecords;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static @Nullable List<ClientRecord> getAllClientsRecords(){
        try {
            String selectAll = getSelectRequest(Clients.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<ClientRecord> clientsRecords = new ArrayList<>();
            while (resultSet.next()){
                clientsRecords.add(new ClientRecord(resultSet.getLong(ID_ROW), Clients.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), resultSet.getString(Clients.NAME_ROW),
                        resultSet.getString(Clients.CONTACTS_DATA_ROW), resultSet.getString(Clients.CAR_NAME_ROW), resultSet.getString(Clients.CAR_NUMBER_ROW)));
            }
            return clientsRecords;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static @Nullable List<MaintainingRecord> getAllMaintainingRecords(){
        try {
            String selectAll = getSelectRequest(Maintaining.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<MaintainingRecord> maintainingRecords = new ArrayList<>();
            while (resultSet.next()){
                maintainingRecords.add(new MaintainingRecord(Maintaining.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), resultSet.getLong(ID_ROW), resultSet.getString(Maintaining.DATE_ROW),
                        resultSet.getString(Maintaining.CLIENT_ROW), resultSet.getString(Maintaining.AUTO_ROW), resultSet.getString(Maintaining.SERVICE_ROW), resultSet.getString(Maintaining.BRANCH_ROW), resultSet.getString(Maintaining.MECHANIC_ROW)));
            }

            return maintainingRecords;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static @Nullable List<ServicesRecord> getAllServicesRecords(){
        try {
            String selectAll = getSelectRequest(Services.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<ServicesRecord> maintainingRecords = new ArrayList<>();
            while (resultSet.next()){
                maintainingRecords.add(new ServicesRecord(Services.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), resultSet.getLong(ID_ROW), resultSet.getString(Services.NAME_ROW),
                        resultSet.getString(Services.DESCRIPTION_ROW), resultSet.getString(Services.DURATION_ROW), resultSet.getDouble(Services.COST_ROW)));
            }

            return maintainingRecords;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static @Nullable List<StaffRecord> getAllStaffsRecords(){
        try {
            String selectAll = getSelectRequest(Staff.TABLE_NAME);
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY))).prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<StaffRecord> maintainingRecords = new ArrayList<>();
            while (resultSet.next()){
                maintainingRecords.add(new StaffRecord(Staff.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), resultSet.getLong(ID_ROW), resultSet.getString(Staff.NAME_ROW), resultSet.getString(Staff.POST_ROW),
                        resultSet.getString(Staff.BRANCH_ROW), resultSet.getString(Staff.CONTACTS_ROW), resultSet.getString(Staff.QUALIFICATION_ROW)));
            }

            return maintainingRecords;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }
}
