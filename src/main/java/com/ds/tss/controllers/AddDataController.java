package com.ds.tss.controllers;

import com.ds.tss.Constants;
import com.ds.tss.MainPage;
import com.ds.tss.database.tablesConstants.*;
import com.ds.tss.dialogs.ErrorDialog;
import com.ds.tss.extendsNodes.ExtendedTextField;
import com.ds.tss.records.*;
import com.ds.tss.utils.Utils;
import com.ds.tss.utils.actionListeners.IOnAction;
import com.ds.tss.utils.settings.SettingsManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

import static com.ds.tss.Constants.*;
import static com.ds.tss.utils.Utils.defaultCategoryMenuItemsAction;

public class AddDataController {
    @FXML
    private MenuButton categoryMenuButton;

    @FXML
    private VBox contentVbox;

    @FXML
    private Button nextButton;
    private IOnAction onClose;

    @FXML
    void initialize() {
        initCategoryMenuButton();

        nextButton.setFont(Font.loadFont(MainPage.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 16d));
    }

    public void setOnClose(IOnAction onClose) {
        this.onClose = onClose;
    }

    private void initCategoryMenuButton() {
        try {
            MenuItem menuItemClients = new MenuItem("Клиенты");
            MenuItem menuItemBranches = new MenuItem("Филиалы");
            MenuItem menuItemServices = new MenuItem("Услуги");
            MenuItem menuItemMaintaining = new MenuItem("Записи на обслуживание");
            MenuItem menuItemStaffs = new MenuItem("Сотрудники");

            menuItemClients.setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(menuItemClients, categoryMenuButton);
                loadClientsComponents();
            });
            menuItemBranches.setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(menuItemBranches, categoryMenuButton);
                loadBranchesComponents();
            });
            menuItemServices.setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(menuItemServices, categoryMenuButton);
                loadServicesComponents();
            });
            menuItemMaintaining.setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(menuItemMaintaining, categoryMenuButton);
                loadMaintainingComponents();
            });
            menuItemStaffs.setOnAction(actionEvent -> {
                defaultCategoryMenuItemsAction(menuItemStaffs, categoryMenuButton);
                loadStaffComponents();
            });

            categoryMenuButton.setText("Выбор");
            categoryMenuButton.setFont(Font.loadFont(MainPage.class.getResourceAsStream(Constants.INTER_BOLD_ITALIC_FONT_INPUT_PATH), 14d));
            categoryMenuButton.getItems().addAll(menuItemClients, menuItemBranches, menuItemServices, menuItemMaintaining, menuItemStaffs);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void loadMaintainingComponents() {
        try {
            clearContentVBox();

            ExtendedTextField extendedTextFieldDate = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Дата", Utils.getImage("images/date.png"));
            ExtendedTextField extendedTextFieldClient = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Клиент", Utils.getImage("images/user.png"));
            ExtendedTextField extendedTextFieldAuto = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Авто", Utils.getImage("images/car.png"));
            ExtendedTextField extendedTextFieldService = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Услуга", Utils.getImage("images/service.png"));
            ExtendedTextField extendedTextFieldBranch = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Филиал", Utils.getImage("images/all_symbols.png"));
            ExtendedTextField extendedTextFieldMechanic = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Механик", Utils.getImage("images/mechanic.png"));

            contentVbox.getChildren().addAll(extendedTextFieldDate, extendedTextFieldClient, extendedTextFieldAuto, extendedTextFieldService, extendedTextFieldBranch, extendedTextFieldMechanic);
            nextButton.setOnAction(actionEvent -> {
                try {
                    List<ExtendedTextField> emptyFields = Utils.getEmptyFieldsFromArray(new ExtendedTextField[]{extendedTextFieldDate, extendedTextFieldClient, extendedTextFieldAuto, extendedTextFieldService, extendedTextFieldBranch, extendedTextFieldMechanic});
                    emptyFields.forEach(ExtendedTextField::setError);

                    if (!emptyFields.isEmpty())
                        return;

                    if (!ClientRecord.findClientWithParameter(Clients.CAR_NAME_ROW, extendedTextFieldClient.getText())) {
                        ErrorDialog.show(new IllegalArgumentException("Такого клиента не существует"));
                        return;
                    }

                    if (!BranchRecord.findBranchWithName(extendedTextFieldBranch.getText())) {
                        ErrorDialog.show(new IllegalArgumentException("Такого филиала не существует"));
                        return;
                    }

                    if (!ServicesRecord.findServicesWithName(extendedTextFieldService.getText())) {
                        ErrorDialog.show(new IllegalArgumentException("Такой услуги не существует"));
                        return;
                    }

                    RecordsWriter.addMaintaining(new MaintainingRecord(Maintaining.TABLE_NAME, SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY), extendedTextFieldDate.getText(), extendedTextFieldClient.getText(),
                            extendedTextFieldAuto.getText(), extendedTextFieldService.getText(), extendedTextFieldBranch.getText(), extendedTextFieldMechanic.getText()), SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY));
                    closeStage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void loadServicesComponents() {
        try {
            clearContentVBox();

            ExtendedTextField extendedTextFieldName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Имя услуги", Utils.getImage("images/all_symbols.png"));
            ExtendedTextField extendedTextFieldDescription = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Описание", Utils.getImage("images/all_symbols.png"));
            ExtendedTextField extendedTextFieldDuration = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Продолжительность", Utils.getImage("images/clock.png"));
            ExtendedTextField extendedTextFieldCost = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Цена (руб.)", Utils.getImage("images/amount.png"));

            contentVbox.getChildren().addAll(extendedTextFieldName, extendedTextFieldDescription, extendedTextFieldDuration, extendedTextFieldCost);
            nextButton.setOnAction(actionEvent -> {
                try {
                    List<ExtendedTextField> emptyFields = Utils.getEmptyFieldsFromArray(new ExtendedTextField[]{extendedTextFieldName, extendedTextFieldDescription, extendedTextFieldDuration, extendedTextFieldCost});
                    emptyFields.forEach(ExtendedTextField::setError);

                    if (!emptyFields.isEmpty())
                        return;

                    if (ServicesRecord.findServicesWithName(extendedTextFieldName.getText())) {
                        ErrorDialog.show(new IllegalArgumentException("Такой услуга уже существует"));
                        return;
                    }

                    Double.parseDouble(extendedTextFieldCost.getText());

                    RecordsWriter.addService(new ServicesRecord(Services.TABLE_NAME, SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY), extendedTextFieldName.getText(), extendedTextFieldDescription.getText(),
                            extendedTextFieldDuration.getText(), Double.parseDouble(extendedTextFieldCost.getText())), SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY));
                    closeStage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void loadClientsComponents() {
        try {
            clearContentVBox();

            ExtendedTextField extendedTextFieldName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Имя клиента", Utils.getImage("images/user.png"));
            ExtendedTextField extendedTextFieldCarName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Машина клиента", Utils.getImage("images/car.png"));
            ExtendedTextField extendedTextFieldCarNumber = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Номер машины", Utils.getImage("images/car_number.png"));
            ExtendedTextField extendedTextFieldContacts = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Контакты клиента", Utils.getImage("images/telephone.png"));

            contentVbox.getChildren().addAll(extendedTextFieldName, extendedTextFieldCarName, extendedTextFieldCarNumber, extendedTextFieldContacts);
            nextButton.setOnAction(actionEvent -> {
                try {
                    List<ExtendedTextField> emptyFields = Utils.getEmptyFieldsFromArray(new ExtendedTextField[]{extendedTextFieldName,  extendedTextFieldCarName, extendedTextFieldCarNumber, extendedTextFieldContacts});
                    emptyFields.forEach(ExtendedTextField::setError);

                    if (!emptyFields.isEmpty())
                        return;

                    if (ClientRecord.findClientWithParameter(Clients.NAME_ROW, extendedTextFieldName.getText())) {
                        ErrorDialog.show(new IllegalArgumentException("Такой клиент уже существует"));
                        return;
                    }

                    if(ClientRecord.findClientWithParameter(Clients.CAR_NUMBER_ROW, extendedTextFieldCarNumber.getText())){
                        ErrorDialog.show(new IllegalArgumentException("Клиент с таким номером машины уже существует"));
                        return;
                    }

                    RecordsWriter.addClient(new ClientRecord(Clients.TABLE_NAME, SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY), extendedTextFieldName.getText(), extendedTextFieldContacts.getText(),
                            extendedTextFieldCarName.getText(), extendedTextFieldCarNumber.getText()), SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY));
                    closeStage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void loadBranchesComponents(){
        try {
            clearContentVBox();

            ExtendedTextField extendedTextFieldName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Название филиала", Utils.getImage("images/all_symbols.png"));
            ExtendedTextField extendedTextFieldAddress = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Адрес", Utils.getImage("images/address.png"));
            ExtendedTextField extendedTextFieldTelephone = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Телефон", Utils.getImage("images/telephone.png"));
            ExtendedTextField extendedTextFieldWorkClocks = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Часы работы", Utils.getImage("images/clock.png"));
            ExtendedTextField extendedTextFieldManager = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Менеджер", Utils.getImage("images/manager.png"));

            contentVbox.getChildren().addAll(extendedTextFieldName,  extendedTextFieldAddress, extendedTextFieldTelephone, extendedTextFieldWorkClocks, extendedTextFieldManager);
            nextButton.setOnAction(actionEvent -> {
                try {
                    List<ExtendedTextField> emptyFields = Utils.getEmptyFieldsFromArray(new ExtendedTextField[]{extendedTextFieldName,  extendedTextFieldAddress, extendedTextFieldTelephone, extendedTextFieldWorkClocks, extendedTextFieldManager});
                    emptyFields.forEach(ExtendedTextField::setError);

                    if (!emptyFields.isEmpty())
                        return;

                    if(!Utils.checkPhoneNumber(extendedTextFieldTelephone.getText())){
                        return;
                    }

                    if (BranchRecord.findBranchWithName(extendedTextFieldName.getText())) {
                        ErrorDialog.show(new IllegalArgumentException("Такой филиал уже существует"));
                        return;
                    }

                    RecordsWriter.addBranch(new BranchRecord(Branches.TABLE_NAME, SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY), extendedTextFieldName.getText(),
                            extendedTextFieldAddress.getText(), extendedTextFieldTelephone.getText(), extendedTextFieldWorkClocks.getText(), extendedTextFieldManager.getText()), SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY));
                    closeStage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void loadStaffComponents(){
        try {
            clearContentVBox();

            ExtendedTextField extendedTextFieldName = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Имя сотрудника", Utils.getImage("images/user.png"));
            ExtendedTextField extendedTextFieldPost = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Должность", Utils.getImage("images/post.png"));
            ExtendedTextField extendedTextFieldBranch = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Филиал", Utils.getImage("images/all_symbols.png"));
            ExtendedTextField extendedTextFieldContacts = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Контакты", Utils.getImage("images/telephone.png"));
            ExtendedTextField extendedTextFieldQualification = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Квалификация", Utils.getImage("images/qualification.png"));
            ExtendedTextField extendedTextFieldSalary = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, "Зарплата (руб.)", Utils.getImage("images/payments.png"));

            contentVbox.getChildren().addAll(extendedTextFieldName, extendedTextFieldPost, extendedTextFieldBranch, extendedTextFieldContacts, extendedTextFieldQualification, extendedTextFieldSalary);
            nextButton.setOnAction(actionEvent -> {
                try {
                    List<ExtendedTextField> emptyFields = Utils.getEmptyFieldsFromArray(new ExtendedTextField[]{extendedTextFieldName, extendedTextFieldPost, extendedTextFieldBranch, extendedTextFieldContacts, extendedTextFieldQualification, extendedTextFieldSalary});
                    emptyFields.forEach(ExtendedTextField::setError);

                    if (!emptyFields.isEmpty())
                        return;

                    if(!BranchRecord.findBranchWithName(extendedTextFieldBranch.getText())) {
                        ErrorDialog.show(new IllegalArgumentException("Такого филиала не существует"));
                        return;
                    }

                    Double.parseDouble(extendedTextFieldSalary.getText());

                    RecordsWriter.addStaff(new StaffRecord(Staff.TABLE_NAME, SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY), extendedTextFieldName.getText(), extendedTextFieldPost.getText(),
                            extendedTextFieldBranch.getText(), extendedTextFieldContacts.getText(), extendedTextFieldQualification.getText(), Double.parseDouble(extendedTextFieldSalary.getText())), SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY));
                    closeStage();
                }catch (Exception e){
                    ErrorDialog.show(e);
                }
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void clearContentVBox(){
        try {
            contentVbox.getChildren().clear();
        }catch (Exception e){
            ErrorDialog.show(e);
        }

    }

    private void closeStage(){
        if(onClose != null)
            onClose.onAction();
        ((Stage) nextButton.getScene().getWindow()).close();
    }

}
