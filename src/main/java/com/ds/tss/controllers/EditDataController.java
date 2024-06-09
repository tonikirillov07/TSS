package com.ds.tss.controllers;

import com.ds.tss.Constants;
import com.ds.tss.MainPage;
import com.ds.tss.database.DatabaseService;
import com.ds.tss.database.tablesConstants.*;
import com.ds.tss.dialogs.ErrorDialog;
import com.ds.tss.extendsNodes.ExtendedTextField;
import com.ds.tss.records.*;
import com.ds.tss.utils.Utils;
import com.ds.tss.utils.actionListeners.IOnAction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class EditDataController {
    @FXML
    private Button buttonNext;

    @FXML
    private VBox contentVbox;

    @FXML
    private Button buttonDelete;
    private IOnAction onClose;

    @FXML
    void initialize() {
        buttonNext.setFont(Font.loadFont(MainPage.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 14d));

        buttonDelete.setFont(Font.loadFont(MainPage.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 14d));
        buttonDelete.setStyle("-fx-background-color: rgb(187, 71, 71);");
    }

    public void setOnClose(IOnAction onClose) {
        this.onClose = onClose;
    }

    private void closeStage(){
        if(onClose != null)
            onClose.onAction();
        ((Stage) buttonNext.getScene().getWindow()).close();
    }

    private void applyDeleteButton(long id, String tableName, String databasePath){
        buttonDelete.setOnAction(actionEvent -> {
            DatabaseService.deleteRecord(id, tableName, databasePath);
            closeStage();
        });
    }

    private boolean hasEmptyFields(ExtendedTextField[] extendedTextFields){
        List<ExtendedTextField> emptyFields = Utils.getEmptyFieldsFromArray(extendedTextFields);
        emptyFields.forEach(ExtendedTextField::setError);

        return !emptyFields.isEmpty();
    }

    public void loadClient(ClientRecord clientRecord){
        try{
            clearContentVBox();

            ExtendedTextField extendedTextFieldName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Имя клиента", Utils.getImage("images/user.png"));
            ExtendedTextField extendedTextFieldCarName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Машина клиента", Utils.getImage("images/car.png"));
            ExtendedTextField extendedTextFieldCarNumber = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Номер машины", Utils.getImage("images/car_number.png"));
            ExtendedTextField extendedTextFieldContacts = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Контакты клиента", Utils.getImage("images/telephone.png"));

            extendedTextFieldName.setText(clientRecord.getName());
            extendedTextFieldCarName.setText(clientRecord.getCarName());
            extendedTextFieldCarNumber.setText(clientRecord.getCarNumber());
            extendedTextFieldContacts.setText(clientRecord.getContacts());

            contentVbox.getChildren().addAll(extendedTextFieldName, extendedTextFieldCarName, extendedTextFieldCarNumber, extendedTextFieldContacts);
            buttonNext.setOnAction(actionEvent -> {
                try {
                    if (hasEmptyFields(new ExtendedTextField[]{extendedTextFieldName, extendedTextFieldCarName, extendedTextFieldCarNumber, extendedTextFieldContacts}))
                        return;

                    DatabaseService.changeValue(Clients.NAME_ROW, extendedTextFieldName.getText(), clientRecord.getId(), Clients.TABLE_NAME, clientRecord.getDatabasePath());
                    DatabaseService.changeValue(Clients.CAR_NAME_ROW, extendedTextFieldCarName.getText(), clientRecord.getId(), Clients.TABLE_NAME, clientRecord.getDatabasePath());
                    DatabaseService.changeValue(Clients.CAR_NUMBER_ROW, extendedTextFieldCarNumber.getText(), clientRecord.getId(), Clients.TABLE_NAME, clientRecord.getDatabasePath());
                    DatabaseService.changeValue(Clients.CONTACTS_DATA_ROW, extendedTextFieldContacts.getText(), clientRecord.getId(), Clients.TABLE_NAME, clientRecord.getDatabasePath());

                    closeStage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });
            applyDeleteButton(clientRecord.getId(), Clients.TABLE_NAME, clientRecord.getDatabasePath());
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public void loadBranch(BranchRecord branchRecord){
        try{
            clearContentVBox();

            ExtendedTextField extendedTextFieldName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Название филиала", Utils.getImage("images/all_symbols.png"));
            ExtendedTextField extendedTextFieldAddress = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Адрес", Utils.getImage("images/address.png"));
            ExtendedTextField extendedTextFieldTelephone = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Телефон", Utils.getImage("images/telephone.png"));
            ExtendedTextField extendedTextFieldWorkClocks = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Часы работы", Utils.getImage("images/clock.png"));
            ExtendedTextField extendedTextFieldManager = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Менеджер", Utils.getImage("images/manager.png"));

            extendedTextFieldName.setText(branchRecord.getName());
            extendedTextFieldAddress.setText(branchRecord.getAddress());
            extendedTextFieldTelephone.setText(branchRecord.getTelephone());
            extendedTextFieldWorkClocks.setText(branchRecord.getWorkClocks());
            extendedTextFieldManager.setText(branchRecord.getManager());

            contentVbox.getChildren().addAll(extendedTextFieldName, extendedTextFieldAddress, extendedTextFieldTelephone, extendedTextFieldWorkClocks, extendedTextFieldManager);
            buttonNext.setOnAction(actionEvent -> {
                try {
                    if (hasEmptyFields(new ExtendedTextField[]{extendedTextFieldName, extendedTextFieldAddress, extendedTextFieldTelephone, extendedTextFieldWorkClocks, extendedTextFieldManager}))
                        return;

                    if(!Utils.checkPhoneNumber(extendedTextFieldTelephone.getText()))
                        return;

                    DatabaseService.changeValue(Branches.NAME_ROW, extendedTextFieldName.getText(), branchRecord.getId(), Branches.TABLE_NAME, branchRecord.getDatabasePath());
                    DatabaseService.changeValue(Branches.ADDRESS_ROW, extendedTextFieldAddress.getText(), branchRecord.getId(), Branches.TABLE_NAME, branchRecord.getDatabasePath());
                    DatabaseService.changeValue(Branches.TELEPHONE_ROW, extendedTextFieldTelephone.getText(), branchRecord.getId(), Branches.TABLE_NAME, branchRecord.getDatabasePath());
                    DatabaseService.changeValue(Branches.WORK_CLOCKS_ROW, extendedTextFieldWorkClocks.getText(), branchRecord.getId(), Branches.TABLE_NAME, branchRecord.getDatabasePath());
                    DatabaseService.changeValue(Branches.MANAGER_ROW, extendedTextFieldManager.getText(), branchRecord.getId(), Branches.TABLE_NAME, branchRecord.getDatabasePath());

                    closeStage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });
            applyDeleteButton(branchRecord.getId(), Branches.TABLE_NAME, branchRecord.getDatabasePath());
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public void loadService(ServicesRecord servicesRecord){
        try{
            clearContentVBox();

            ExtendedTextField extendedTextFieldName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Имя услуги", Utils.getImage("images/all_symbols.png"));
            ExtendedTextField extendedTextFieldDescription = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Описание", Utils.getImage("images/all_symbols.png"));
            ExtendedTextField extendedTextFieldDuration = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Продолжительность", Utils.getImage("images/clock.png"));
            ExtendedTextField extendedTextFieldCost = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Цена (руб.)", Utils.getImage("images/amount.png"));

            extendedTextFieldName.setText(servicesRecord.getName());
            extendedTextFieldDescription.setText(servicesRecord.getDescription());
            extendedTextFieldDuration.setText(servicesRecord.getDuration());
            extendedTextFieldCost.setText(String.valueOf(servicesRecord.getCost()));

            contentVbox.getChildren().addAll(extendedTextFieldName, extendedTextFieldDescription, extendedTextFieldDuration, extendedTextFieldCost);
            buttonNext.setOnAction(actionEvent -> {
                try {
                    if (hasEmptyFields(new ExtendedTextField[]{extendedTextFieldName, extendedTextFieldDescription, extendedTextFieldCost, extendedTextFieldDuration}))
                        return;

                    Double.parseDouble(extendedTextFieldCost.getText());

                    DatabaseService.changeValue(Services.NAME_ROW, extendedTextFieldName.getText(), servicesRecord.getId(), Services.TABLE_NAME, servicesRecord.getDatabasePath());
                    DatabaseService.changeValue(Services.DESCRIPTION_ROW, extendedTextFieldDescription.getText(), servicesRecord.getId(), Services.TABLE_NAME, servicesRecord.getDatabasePath());
                    DatabaseService.changeValue(Services.COST_ROW, extendedTextFieldCost.getText(), servicesRecord.getId(), Services.TABLE_NAME, servicesRecord.getDatabasePath());
                    DatabaseService.changeValue(Services.DURATION_ROW, extendedTextFieldDuration.getText(), servicesRecord.getId(), Services.TABLE_NAME, servicesRecord.getDatabasePath());

                    closeStage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });
            applyDeleteButton(servicesRecord.getId(), Services.TABLE_NAME, servicesRecord.getDatabasePath());
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public void loadStaff(StaffRecord staffRecord) {
        try{
            clearContentVBox();

            ExtendedTextField extendedTextFieldName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Имя сотрудника", Utils.getImage("images/user.png"));
            ExtendedTextField extendedTextFieldPost = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Должность", Utils.getImage("images/post.png"));
            ExtendedTextField extendedTextFieldBranch = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Филиал", Utils.getImage("images/all_symbols.png"));
            ExtendedTextField extendedTextFieldContacts = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Контакты", Utils.getImage("images/telephone.png"));
            ExtendedTextField extendedTextFieldQualification = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Квалификация", Utils.getImage("images/qualification.png"));
            ExtendedTextField extendedTextFieldSalary = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Зарплата (руб.)", Utils.getImage("images/payment.png"));

            extendedTextFieldName.setText(staffRecord.getName());
            extendedTextFieldPost.setText(staffRecord.getPost());
            extendedTextFieldBranch.setText(staffRecord.getBranch());
            extendedTextFieldContacts.setText(staffRecord.getContacts());
            extendedTextFieldQualification.setText(staffRecord.getQualification());
            extendedTextFieldSalary.setText(String.valueOf(staffRecord.getSalary()));

            contentVbox.getChildren().addAll(extendedTextFieldName, extendedTextFieldPost, extendedTextFieldBranch, extendedTextFieldQualification, extendedTextFieldContacts, extendedTextFieldSalary);
            buttonNext.setOnAction(actionEvent -> {
                try {
                    if (hasEmptyFields(new ExtendedTextField[]{extendedTextFieldName, extendedTextFieldPost, extendedTextFieldQualification, extendedTextFieldBranch, extendedTextFieldContacts, extendedTextFieldSalary}))
                        return;

                    Double.parseDouble(extendedTextFieldSalary.getText());

                    DatabaseService.changeValue(Staff.NAME_ROW, extendedTextFieldName.getText(), staffRecord.getId(), Staff.TABLE_NAME, staffRecord.getDatabasePath());
                    DatabaseService.changeValue(Staff.POST_ROW, extendedTextFieldPost.getText(), staffRecord.getId(), Staff.TABLE_NAME, staffRecord.getDatabasePath());
                    DatabaseService.changeValue(Staff.BRANCH_ROW, extendedTextFieldBranch.getText(), staffRecord.getId(), Staff.TABLE_NAME, staffRecord.getDatabasePath());
                    DatabaseService.changeValue(Staff.CONTACTS_ROW, extendedTextFieldContacts.getText(), staffRecord.getId(), Staff.TABLE_NAME, staffRecord.getDatabasePath());
                    DatabaseService.changeValue(Staff.QUALIFICATION_ROW, extendedTextFieldQualification.getText(), staffRecord.getId(), Staff.TABLE_NAME, staffRecord.getDatabasePath());
                    DatabaseService.changeValue(Staff.SALARY_ROW, extendedTextFieldSalary.getText(), staffRecord.getId(), Staff.TABLE_NAME, staffRecord.getDatabasePath());

                    closeStage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });
            applyDeleteButton(staffRecord.getId(), Staff.TABLE_NAME, staffRecord.getDatabasePath());
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public void loadMaintaining(MaintainingRecord maintainingRecord) {
        try{
            clearContentVBox();

            ExtendedTextField extendedTextFieldDate = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Дата", Utils.getImage("images/date.png"));
            ExtendedTextField extendedTextFieldClient = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Клиент", Utils.getImage("images/user.png"));
            ExtendedTextField extendedTextFieldAuto = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Авто", Utils.getImage("images/car.png"));
            ExtendedTextField extendedTextFieldService = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Услуга", Utils.getImage("images/service.png"));
            ExtendedTextField extendedTextFieldBranch = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Филиал", Utils.getImage("images/all_symbols.png"));
            ExtendedTextField extendedTextFieldMechanic = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Механик", Utils.getImage("images/mechanic.png"));

            extendedTextFieldDate.setText(maintainingRecord.getDate());
            extendedTextFieldClient.setText(maintainingRecord.getClient());
            extendedTextFieldAuto.setText(maintainingRecord.getAuto());
            extendedTextFieldService.setText(maintainingRecord.getService());
            extendedTextFieldBranch.setText(maintainingRecord.getBranch());
            extendedTextFieldMechanic.setText(maintainingRecord.getMechanic());

            contentVbox.getChildren().addAll(extendedTextFieldDate, extendedTextFieldClient, extendedTextFieldAuto, extendedTextFieldBranch, extendedTextFieldService, extendedTextFieldMechanic);
            buttonNext.setOnAction(actionEvent -> {
                try {
                    if (hasEmptyFields(new ExtendedTextField[]{extendedTextFieldDate, extendedTextFieldClient, extendedTextFieldBranch, extendedTextFieldAuto, extendedTextFieldService, extendedTextFieldMechanic}))
                        return;

                    DatabaseService.changeValue(Maintaining.DATE_ROW, extendedTextFieldDate.getText(), maintainingRecord.getId(), Maintaining.TABLE_NAME, maintainingRecord.getDatabasePath());
                    DatabaseService.changeValue(Maintaining.CLIENT_ROW, extendedTextFieldClient.getText(), maintainingRecord.getId(), Maintaining.TABLE_NAME, maintainingRecord.getDatabasePath());
                    DatabaseService.changeValue(Maintaining.AUTO_ROW, extendedTextFieldAuto.getText(), maintainingRecord.getId(), Maintaining.TABLE_NAME, maintainingRecord.getDatabasePath());
                    DatabaseService.changeValue(Maintaining.SERVICE_ROW, extendedTextFieldService.getText(), maintainingRecord.getId(), Maintaining.TABLE_NAME, maintainingRecord.getDatabasePath());
                    DatabaseService.changeValue(Maintaining.BRANCH_ROW, extendedTextFieldBranch.getText(), maintainingRecord.getId(), Maintaining.TABLE_NAME, maintainingRecord.getDatabasePath());
                    DatabaseService.changeValue(Maintaining.MECHANIC_ROW, extendedTextFieldMechanic.getText(), maintainingRecord.getId(), Maintaining.TABLE_NAME, maintainingRecord.getDatabasePath());

                    closeStage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });
            applyDeleteButton(maintainingRecord.getId(), Staff.TABLE_NAME, maintainingRecord.getDatabasePath());
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void clearContentVBox(){
        contentVbox.getChildren().clear();
    }
}
