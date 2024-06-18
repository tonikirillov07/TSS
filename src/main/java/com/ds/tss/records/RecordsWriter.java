package com.ds.tss.records;

import com.ds.tss.database.DatabaseService;
import com.ds.tss.database.tablesConstants.*;
import com.ds.tss.dialogs.ErrorDialog;

import java.sql.PreparedStatement;
import java.util.Objects;

public class RecordsWriter {
    public static void addBranch(BranchRecord branchRecord, String databasePath){
        try {
            String add = "INSERT INTO " + Branches.TABLE_NAME + "(" + Branches.NAME_ROW + "," + Branches.ADDRESS_ROW + "," + Branches.TELEPHONE_ROW + "," + Branches.WORK_CLOCKS_ROW + "," + Branches.MANAGER_ROW + ") VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(databasePath)).prepareStatement(add);
            preparedStatement.setString(1, branchRecord.getName());
            preparedStatement.setString(2, branchRecord.getAddress());
            preparedStatement.setString(3, branchRecord.getTelephone());
            preparedStatement.setString(4, branchRecord.getWorkClocks());
            preparedStatement.setString(5, branchRecord.getManager());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void addClient(ClientRecord clientRecord, String databasePath){
        try {
            String add = "INSERT INTO " + Clients.TABLE_NAME + "(" + Clients.NAME_ROW + "," + Clients.CAR_NAME_ROW + "," + Clients.CAR_NUMBER_ROW + "," + Clients.CONTACTS_DATA_ROW + ") VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(databasePath)).prepareStatement(add);
            preparedStatement.setString(1, clientRecord.getName());
            preparedStatement.setString(2, clientRecord.getName());
            preparedStatement.setString(3, clientRecord.getCarNumber());
            preparedStatement.setString(4, clientRecord.getContacts());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void addMaintaining(MaintainingRecord maintainingRecord, String databasePath){
        try {
            String add = "INSERT INTO " + Maintaining.TABLE_NAME + "(" + Maintaining.DATE_ROW + "," + Maintaining.CLIENT_ROW + "," + Maintaining.AUTO_ROW + "," + Maintaining.SERVICE_ROW + "," + Maintaining.BRANCH_ROW + "," + Maintaining.MECHANIC_ROW + ") VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(databasePath)).prepareStatement(add);
            preparedStatement.setString(1, maintainingRecord.getDate());
            preparedStatement.setString(2, maintainingRecord.getClient());
            preparedStatement.setString(3, maintainingRecord.getAuto());
            preparedStatement.setString(4, maintainingRecord.getService());
            preparedStatement.setString(5, maintainingRecord.getBranch());
            preparedStatement.setString(6, maintainingRecord.getMechanic());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void addService(ServicesRecord servicesRecord, String databasePath){
        try{
            String add = "INSERT INTO " + Services.TABLE_NAME + "(" + Services.NAME_ROW + "," + Services.DESCRIPTION_ROW + "," + Services.DURATION_ROW + "," + Services.COST_ROW + ") VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(databasePath)).prepareStatement(add);
            preparedStatement.setString(1, servicesRecord.getName());
            preparedStatement.setString(2, servicesRecord.getDescription());
            preparedStatement.setString(3, servicesRecord.getDuration());
            preparedStatement.setDouble(4, servicesRecord.getCost());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void addStaff(StaffRecord staffRecord, String databasePath){
        try{
            String add = "INSERT INTO " + Staff.TABLE_NAME + "(" + Staff.NAME_ROW + "," + Staff.POST_ROW + "," + Staff.BRANCH_ROW + "," + Staff.CONTACTS_ROW + "," + Staff.QUALIFICATION_ROW + ", " + Staff.WORKED_TIME_ROW + "," + Staff.AWARDS_ROW + "," + Staff.PASSED_WORKS + ", " + Staff.SALARY_ROW + ") VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = Objects.requireNonNull(DatabaseService.getConnection(databasePath)).prepareStatement(add);
            preparedStatement.setString(1, staffRecord.getName());
            preparedStatement.setString(2, staffRecord.getPost());
            preparedStatement.setString(3, staffRecord.getBranch());
            preparedStatement.setString(4, staffRecord.getContacts());
            preparedStatement.setString(5, staffRecord.getQualification());
            preparedStatement.setDouble(6, staffRecord.getWorkedTime());
            preparedStatement.setDouble(7, staffRecord.getAwards());
            preparedStatement.setInt(8, staffRecord.getPassedWorks());
            preparedStatement.setDouble(9, staffRecord.getSalary());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}
