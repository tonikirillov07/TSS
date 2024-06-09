package com.ds.tss.controllers;

import com.ds.tss.Constants;
import com.ds.tss.MainPage;
import com.ds.tss.database.DatabaseConstants;
import com.ds.tss.database.DatabaseService;
import com.ds.tss.database.tablesConstants.Clients;
import com.ds.tss.extendsNodes.ExtendedTextField;
import com.ds.tss.records.ClientRecord;
import com.ds.tss.utils.RecordsTypes;
import com.ds.tss.utils.Utils;
import com.ds.tss.utils.actionListeners.IOnAction;
import com.ds.tss.utils.actionListeners.IOnTextTyping;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

import static com.ds.tss.MainPage.getSelectedRowIndexFromTableView;

public class FindClientController {
    @FXML
    private TableView<ClientRecord> clientTableView;

    @FXML
    private MenuButton findClientByElementMenuButton;

    @FXML
    private Label titleLabel;

    @FXML
    private VBox mainVbox;

    private long currentClientId = -1;

    private static final String TELEPHONE_TEXT_FILED_ID = "telephoneTextField";
    private static final String CAR_NUMBER_TEXT_FILED_ID = "carNumberTextField";

    @FXML
    void initialize() {
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.loadFont(MainPage.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 32d));

        findClientByElementMenuButton.setText("Найти клиента по...");
        findClientByElementMenuButton.setFont(Font.loadFont(MainPage.class.getResourceAsStream(Constants.INTER_BOLD_ITALIC_FONT_INPUT_PATH), 16d));

        initMenuButton();
        initTableView();
    }

    private @NotNull ExtendedTextField createTextField(String placeholder, String image, String id){
        ExtendedTextField extendedTextField = new ExtendedTextField(ExtendedTextField.DEFAULT_WIDTH, ExtendedTextField.DEFAULT_HEIGHT, placeholder, Utils.getImage(image));
        extendedTextField.setId(id);
        VBox.setMargin(extendedTextField, new Insets(25, 45d, 15d, 45d));

        getMainVboxChildren().add(getMainVboxChildren().indexOf(findClientByElementMenuButton) + 1, extendedTextField);

        return extendedTextField;
    }

    private void createByTelephoneTextField(){
        if(Utils.paneHasObjectWithId(CAR_NUMBER_TEXT_FILED_ID, mainVbox))
            getMainVboxChildren().remove(Utils.findNodeWithIdInPane(CAR_NUMBER_TEXT_FILED_ID, mainVbox));

        if(Utils.paneHasObjectWithId(TELEPHONE_TEXT_FILED_ID, mainVbox))
            return;

        ExtendedTextField extendedTextField = createTextField("Телефон", "images/telephone.png", TELEPHONE_TEXT_FILED_ID);
        extendedTextField.setOnTextTyping(text -> {
            clientTableView.getItems().clear();

            if(ClientRecord.findClientWithParameter(Clients.CONTACTS_DATA_ROW, text)){
                ClientRecord foundClient = ClientRecord.findClientRecordWithParameter(Clients.CONTACTS_DATA_ROW, text);

                assert foundClient != null;
                currentClientId = foundClient.getId();

                clientTableView.getItems().add(foundClient);
            }
        });

    }

    private void createByCarNumberTextField(){
        if(Utils.paneHasObjectWithId(TELEPHONE_TEXT_FILED_ID, mainVbox))
            getMainVboxChildren().remove(Utils.findNodeWithIdInPane(TELEPHONE_TEXT_FILED_ID, mainVbox));

        if(Utils.paneHasObjectWithId(CAR_NUMBER_TEXT_FILED_ID, mainVbox))
            return;

        ExtendedTextField extendedTextField = createTextField("Номер машины", "images/car_number.png", CAR_NUMBER_TEXT_FILED_ID);
        extendedTextField.setOnTextTyping(text -> {
            clientTableView.getItems().clear();

            if(ClientRecord.findClientWithParameter(Clients.CAR_NUMBER_ROW, text)){
                clientTableView.getItems().add(ClientRecord.findClientRecordWithParameter(Clients.CAR_NUMBER_ROW, text));
            }
        });
    }

    private ObservableList<Node> getMainVboxChildren(){
        return mainVbox.getChildren();
    }

    private void initTableView() {
        clientTableView.getColumns().clear();

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

        clientTableView.getColumns().addAll(agentRecordIdTableColumn, agentRecordNameTableColumn, agentRecordCarNameTableColumn, agentRecordCarCodeTableColumn, agentRecordContactsTableColumn);
        clientTableView.setOnMouseClicked(mouseEvent -> {
            int cellIndex = getSelectedRowIndexFromTableView(clientTableView);
            if(cellIndex < 0)
                return;

            ClientRecord clientRecord = clientTableView.getItems().get(cellIndex);
            Utils.openEditingScene(clientRecord, RecordsTypes.CLIENT, new IOnAction() {
                @Override
                public void onAction() {
                    clientTableView.getItems().clear();
                    clientTableView.getItems().add(ClientRecord.findClientRecordWithParameter(DatabaseConstants.ID_ROW, String.valueOf(currentClientId)));
                }
            });
        });
    }

    private void initMenuButton() {
        MenuItem menuItemTelephone = new MenuItem("По номеру телефона");
        MenuItem menuItemCarNumber = new MenuItem("По номеру машины");

        menuItemTelephone.setOnAction(actionEvent -> createByTelephoneTextField());
        menuItemCarNumber.setOnAction(actionEvent -> createByCarNumberTextField());

        findClientByElementMenuButton.getItems().addAll(menuItemTelephone, menuItemCarNumber);
    }

}
