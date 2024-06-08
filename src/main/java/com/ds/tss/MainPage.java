package com.ds.tss;

import com.ds.tss.controllers.AddDataController;
import com.ds.tss.controllers.EditDataController;
import com.ds.tss.dialogs.ErrorDialog;
import com.ds.tss.records.*;
import com.ds.tss.utils.AnotherScenes;
import com.ds.tss.utils.RecordsTypes;
import com.ds.tss.utils.Utils;
import com.ds.tss.utils.settings.SettingsManager;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static com.ds.tss.Constants.CURRENT_DATABASE_FILE_KEY;
import static com.ds.tss.Constants.TEMPORARY_DATABASE;
import static com.ds.tss.utils.Utils.*;

public class MainPage {
    private final Label categoryLabel;
    private final MenuBar menuBar;
    private final MenuButton categoryMenuButton;
    private final VBox mainVbox;
    private RecordsTypes currentRecordType;

    public MainPage(Label categoryLabel, MenuBar menuBar, MenuButton categoryMenuButton, VBox mainVbox) {
        this.categoryLabel = categoryLabel;
        this.menuBar = menuBar;
        this.categoryMenuButton = categoryMenuButton;
        this.mainVbox = mainVbox;
    }

    public void init(){
        checkDatabaseProperty();
        initCategoryLabel();
        initMenuBar();
        initCategoryMenuButton();
    }

    private void checkDatabaseProperty() {
        if(!new File(Objects.requireNonNull(SettingsManager.getValue(CURRENT_DATABASE_FILE_KEY))).exists()){
            SettingsManager.changeValue(CURRENT_DATABASE_FILE_KEY, TEMPORARY_DATABASE);
        }
    }

    private void initCategoryMenuButton() {
        try {
            MenuItem menuItemBranches = new MenuItem("Филиалы");
            MenuItem menuItemServices = new MenuItem("Услуги");
            MenuItem menuItemClients = new MenuItem("Клиенты");
            MenuItem menuItemMaintainingRecords = new MenuItem("Записи на обслуживание");
            MenuItem menuItemStaffs = new MenuItem("Сотрудники");

            menuItemBranches.setOnAction(actionEvent -> {
                displayBranches();
                defaultCategoryMenuItemsAction(menuItemBranches, categoryMenuButton);
            });
            menuItemClients.setOnAction(actionEvent -> {
                displayClients();
                defaultCategoryMenuItemsAction(menuItemClients, categoryMenuButton);
            });
            menuItemServices.setOnAction(actionEvent -> {
                displayServices();
                defaultCategoryMenuItemsAction(menuItemServices, categoryMenuButton);
            });
            menuItemMaintainingRecords.setOnAction(actionEvent -> {
                displayMaintaining();
                defaultCategoryMenuItemsAction(menuItemMaintainingRecords, categoryMenuButton);
            });
            menuItemStaffs.setOnAction(actionEvent -> {
                displayStaffs();
                defaultCategoryMenuItemsAction(menuItemStaffs, categoryMenuButton);
            });

            categoryMenuButton.setText("Выбор");
            categoryMenuButton.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_BOLD_ITALIC_FONT_INPUT_PATH), 14d));
            categoryMenuButton.getItems().clear();
            categoryMenuButton.getItems().addAll(menuItemBranches, menuItemServices, menuItemClients, menuItemMaintainingRecords, menuItemStaffs);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void displayStaffs() {
        try{
            currentRecordType = RecordsTypes.STAFF;

            TableView<StaffRecord> tableView = new TableView<>();
            applySettingForTableView(tableView);

            TableColumn<StaffRecord, Long> staffRecordIdTableColumn = new TableColumn<>("ID");
            staffRecordIdTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));

            TableColumn<StaffRecord, String> staffRecordNameTableColumn = new TableColumn<>("Имя");
            staffRecordNameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

            TableColumn<StaffRecord, String> staffRecordPostTableColumn = new TableColumn<>("Должность");
            staffRecordPostTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPost()));

            TableColumn<StaffRecord, String> staffRecordBranchTableColumn = new TableColumn<>("Филиал");
            staffRecordBranchTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBranch()));

            TableColumn<StaffRecord, String> staffRecordContactsTableColumn = new TableColumn<>("Контакты");
            staffRecordContactsTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContacts()));

            TableColumn<StaffRecord, String> staffRecordQualificationTableColumn = new TableColumn<>("Квалификация");
            staffRecordQualificationTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQualification()));

            tableView.getColumns().addAll(staffRecordIdTableColumn, staffRecordNameTableColumn, staffRecordPostTableColumn, staffRecordBranchTableColumn,
                    staffRecordContactsTableColumn, staffRecordQualificationTableColumn);
            tableView.setOnMouseClicked(mouseEvent -> {
                int cellIndex = getSelectedRowIndexFromTableView(tableView);
                if(cellIndex < 0)
                    return;

                StaffRecord servicesRecord = tableView.getItems().get(cellIndex);
                openEditingScene(servicesRecord, RecordsTypes.STAFF);
            });
            tableView.getItems().addAll(Objects.requireNonNull(RecordsGetter.getAllStaffsRecords()));
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void displayMaintaining() {
        try{
            currentRecordType = RecordsTypes.MAINTAINING;

            TableView<MaintainingRecord> tableView = new TableView<>();
            applySettingForTableView(tableView);

            TableColumn<MaintainingRecord, Long> maintainingRecordIdTableColumn = new TableColumn<>("ID");
            maintainingRecordIdTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));

            TableColumn<MaintainingRecord, String> maintainingRecordDateTableColumn = new TableColumn<>("Дата");
            maintainingRecordDateTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));

            TableColumn<MaintainingRecord, String> maintainingRecordClientTableColumn = new TableColumn<>("Клиент");
            maintainingRecordClientTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient()));

            TableColumn<MaintainingRecord, String> maintainingRecordAutoTableColumn = new TableColumn<>("Машина");
            maintainingRecordAutoTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuto()));

            TableColumn<MaintainingRecord, String> maintainingRecordServiceTableColumn = new TableColumn<>("Услуга");
            maintainingRecordServiceTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getService()));

            TableColumn<MaintainingRecord, String> maintainingRecordBranchTableColumn = new TableColumn<>("Филиал");
            maintainingRecordBranchTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBranch()));

            TableColumn<MaintainingRecord, String> maintainingRecordMechanicTableColumn = new TableColumn<>("Механик");
            maintainingRecordMechanicTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMechanic()));

            tableView.getColumns().addAll(maintainingRecordIdTableColumn, maintainingRecordDateTableColumn, maintainingRecordClientTableColumn, maintainingRecordAutoTableColumn,
                    maintainingRecordServiceTableColumn, maintainingRecordBranchTableColumn, maintainingRecordMechanicTableColumn);
            tableView.setOnMouseClicked(mouseEvent -> {
                int cellIndex = getSelectedRowIndexFromTableView(tableView);
                if(cellIndex < 0)
                    return;

                MaintainingRecord servicesRecord = tableView.getItems().get(cellIndex);
                openEditingScene(servicesRecord, RecordsTypes.MAINTAINING);
            });
            tableView.getItems().addAll(Objects.requireNonNull(RecordsGetter.getAllMaintainingRecords()));
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void applySettingForTableView(@NotNull TableView tableView){
        try {
            mainVbox.getChildren().removeIf(predicate -> predicate instanceof TableView);

            tableView.setOpacity(0.76d);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            VBox.setVgrow(tableView, Priority.ALWAYS);
            VBox.setMargin(tableView, new Insets(15));

            mainVbox.getChildren().add(tableView);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void displayServices(){
        try{
            currentRecordType = RecordsTypes.SERVICE;

            TableView<ServicesRecord> tableView = new TableView<>();
            applySettingForTableView(tableView);

            TableColumn<ServicesRecord, Long> servicesRecordIdTableColumn = new TableColumn<>("ID");
            servicesRecordIdTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));

            TableColumn<ServicesRecord, String> servicesRecordNameTableColumn = new TableColumn<>("Название услуги");
            servicesRecordNameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

            TableColumn<ServicesRecord, String> servicesRecordDescriptionTableColumn = new TableColumn<>("Описание");
            servicesRecordDescriptionTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

            TableColumn<ServicesRecord, String> servicesRecordDurationTableColumn = new TableColumn<>("Продолжительность");
            servicesRecordDurationTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuration()));

            TableColumn<ServicesRecord, Double> servicesRecordCostTableColumn = new TableColumn<>("Цена");
            servicesRecordCostTableColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getCost()).asObject());

            tableView.getColumns().addAll(servicesRecordIdTableColumn, servicesRecordNameTableColumn, servicesRecordDescriptionTableColumn, servicesRecordDurationTableColumn, servicesRecordCostTableColumn);
            tableView.setOnMouseClicked(mouseEvent -> {
                int cellIndex = getSelectedRowIndexFromTableView(tableView);
                if(cellIndex < 0)
                    return;

                ServicesRecord servicesRecord = tableView.getItems().get(cellIndex);
                openEditingScene(servicesRecord, RecordsTypes.SERVICE);
            });
            tableView.getItems().addAll(Objects.requireNonNull(RecordsGetter.getAllServicesRecords()));
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void displayBranches(){
        try{
            currentRecordType = RecordsTypes.BRANCH;

            TableView<BranchRecord> tableView = new TableView<>();
            applySettingForTableView(tableView);

            TableColumn<BranchRecord, Long> branchesRecordIdTableColumn = new TableColumn<>("ID");
            branchesRecordIdTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));

            TableColumn<BranchRecord, String> branchesRecordNameTableColumn = new TableColumn<>("Название");
            branchesRecordNameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

            TableColumn<BranchRecord, String> branchesRecordAddressTableColumn = new TableColumn<>("Адрес");
            branchesRecordAddressTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

            TableColumn<BranchRecord, String> branchesRecordTelephoneTableColumn = new TableColumn<>("Телефон");
            branchesRecordTelephoneTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));

            TableColumn<BranchRecord, String> branchesRecordWorkClocksTableColumn = new TableColumn<>("Часы работы");
            branchesRecordWorkClocksTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWorkClocks()));

            TableColumn<BranchRecord, String> branchesRecordManagerTableColumn = new TableColumn<>("Менеджер");
            branchesRecordManagerTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getManager()));

            tableView.getColumns().addAll(branchesRecordIdTableColumn, branchesRecordNameTableColumn, branchesRecordAddressTableColumn, branchesRecordTelephoneTableColumn,
                    branchesRecordWorkClocksTableColumn, branchesRecordManagerTableColumn);
            tableView.setOnMouseClicked(mouseEvent -> {
                int cellIndex = getSelectedRowIndexFromTableView(tableView);
                if(cellIndex < 0)
                    return;

                BranchRecord data = tableView.getItems().get(cellIndex);
                openEditingScene(data, RecordsTypes.BRANCH);
            });

            tableView.getItems().addAll(Objects.requireNonNull(RecordsGetter.getAllBranchesRecords()));
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private int getSelectedRowIndexFromTableView(@NotNull TableView tableView){
        try {
            ObservableList tablePositionObservableList = tableView.getSelectionModel().getSelectedCells();
            if (tablePositionObservableList.isEmpty())
                return -1;

            TablePosition tablePosition = (TablePosition) tablePositionObservableList.get(0);
            return tablePosition.getRow();
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return -1;
    }

    private void displayClients(){
        try {
            currentRecordType = RecordsTypes.CLIENT;

            TableView<ClientRecord> tableView = new TableView<>();
            applySettingForTableView(tableView);

            TableColumn<ClientRecord, Long> agentRecordIdTableColumn = new TableColumn<>("ID");
            agentRecordIdTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));

            TableColumn<ClientRecord, String> agentRecordNameTableColumn = new TableColumn<>("Имя");
            agentRecordNameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

            TableColumn<ClientRecord, String> agentRecordCarNameTableColumn = new TableColumn<>("Машина");
            agentRecordCarNameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCarName()));

            TableColumn<ClientRecord, String> agentRecordCarCodeTableColumn = new TableColumn<>("Номер машины");
            agentRecordCarCodeTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCarNumber()));

            TableColumn<ClientRecord, String> agentRecordContactsTableColumn = new TableColumn<>("Контакты");
            agentRecordContactsTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContacts()));

            tableView.getColumns().addAll(agentRecordIdTableColumn, agentRecordNameTableColumn, agentRecordCarNameTableColumn, agentRecordCarCodeTableColumn, agentRecordContactsTableColumn);
            tableView.setOnMouseClicked(mouseEvent -> {
                int cellIndex = getSelectedRowIndexFromTableView(tableView);
                if(cellIndex < 0)
                    return;

                ClientRecord condolesRecord = tableView.getItems().get(cellIndex);
                openEditingScene(condolesRecord, RecordsTypes.CLIENT);
            });
            tableView.getItems().addAll(Objects.requireNonNull(RecordsGetter.getAllClientsRecords()));
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void initMenuBar() {
        try {
            Menu menuFile = new Menu("Файл");
            Menu menuData = new Menu("Данные");
            Menu menuHelp = new Menu("Помощь");

            menuBar.getMenus().clear();
            menuBar.getMenus().addAll(menuFile, menuData, menuHelp);

            initMenuFile(menuFile);
            initMenuData(menuData);
            initMenuHelp(menuHelp);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void initMenuHelp(Menu menuHelp) {
        try{
            MenuItem menuItemContacts = new MenuItem("Контакты");
            menuItemContacts.setOnAction(actionEvent -> AnotherScenes.goToAnotherScene("help-view.fxml", "Контакты"));

            menuHelp.getItems().add(menuItemContacts);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void initMenuData(@NotNull Menu menuData){
        try {
            MenuItem menuItemAdd = new MenuItem("Добавить");
            menuItemAdd.setOnAction(actionEvent -> {
                AddDataController addDataController = (AddDataController) AnotherScenes.goToAnotherScene("add-data-view.fxml", "Добавить запись");
                addDataController.setOnClose(this::update);
            });

            menuData.getItems().addAll(menuItemAdd);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void initMenuFile(@NotNull Menu menuFile){
        try {
            MenuItem menuItemSave = new MenuItem("Сохранить");
            MenuItem menuItemLoad = new MenuItem("Загрузить");

            menuItemLoad.setOnAction(actionEvent -> loadOutsideFile());
            menuItemSave.setOnAction(actionEvent -> saveCurrentFile());

            menuFile.getItems().addAll(menuItemSave, menuItemLoad);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void loadOutsideFile(){
        File selectedFile = openFileDialog("Выберет файл", getStage(), List.of(new FileChooser.ExtensionFilter("База данных", "*.db*")));
        if(selectedFile != null){
            SettingsManager.changeValue(CURRENT_DATABASE_FILE_KEY, selectedFile.getAbsolutePath());
        }
    }

    private void update(){
        if(currentRecordType == null)
            return;

        switch (currentRecordType){
            case BRANCH -> displayBranches();
            case CLIENT -> displayClients();
            case SERVICE -> displayServices();
            case STAFF -> displayStaffs();
            case MAINTAINING -> displayMaintaining();
        }
    }

    private void saveCurrentFile(){
        File selectedFile = openSaveDialog("Выберет файл для сохранения", getStage(), List.of(new FileChooser.ExtensionFilter("База данных", "*.db*")));
        if(selectedFile != null){
            Utils.saveCurrentFile(selectedFile.getAbsolutePath());
        }
    }

    private void openEditingScene(com.ds.tss.records.Record record, @NotNull RecordsTypes recordsTypes){
        EditDataController editDataController = (EditDataController) AnotherScenes.goToAnotherScene("edit-data-view.fxml", "Редактирование данных");

        assert editDataController != null;
        editDataController.setOnClose(this::update);

        switch (recordsTypes){
            case CLIENT -> editDataController.loadClient((ClientRecord) record);
            case BRANCH -> editDataController.loadBranch((BranchRecord) record);
            case SERVICE -> editDataController.loadService((ServicesRecord) record);
            case STAFF -> editDataController.loadStaff((StaffRecord) record);
            case MAINTAINING -> editDataController.loadMaintaining((MaintainingRecord) record);
        }
    }

    private Stage getStage(){
        return (Stage) mainVbox.getScene().getWindow();
    }

    private void initCategoryLabel() {
        try {
            categoryLabel.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 16d));
            categoryLabel.setTextFill(Color.WHITE);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }
}
