package com.ds.tss.utils;

import com.ds.tss.Constants;
import com.ds.tss.Main;
import com.ds.tss.controllers.EditDataController;
import com.ds.tss.dialogs.ErrorDialog;
import com.ds.tss.dialogs.InfoDialog;
import com.ds.tss.extendsNodes.ExtendedTextField;
import com.ds.tss.records.*;
import com.ds.tss.utils.actionListeners.IOnAction;
import com.ds.tss.utils.settings.SettingsManager;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Utils {
    public static void copyString(String string){
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        systemClipboard.setContents(new StringSelection(string), null);
    }

    @Contract("_ -> new")
    public static @Nullable Image getImage(String path){
        try {
            return new Image(Objects.requireNonNull(Main.class.getResourceAsStream(path)));
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static void addActionToNode(@NotNull Node node, IOnAction onAction){
        try {
            node.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY)
                    onAction.onAction();
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void defaultCategoryMenuItemsAction(@NotNull MenuItem menuItem, @NotNull MenuButton menuButton){
        menuButton.setText(menuItem.getText());
    }

    public static @NotNull List<ExtendedTextField> getEmptyFieldsFromArray(ExtendedTextField[] textFields){
        List<ExtendedTextField> textInputControlList = new ArrayList<>();

        Arrays.stream(textFields).toList().forEach(textInputControl -> {
            if (textInputControl == null)
                return;

            if (textInputControl.getText().trim().isEmpty())
                textInputControlList.add(textInputControl);
        });

        return textInputControlList;
    }

    public static void saveCurrentFile(String pathToSave){
        try {
            File file = new File(pathToSave);

            if(file.exists())
                file.delete();

            File currentDatabase = new File(Objects.requireNonNull(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY)));
            Files.copy(currentDatabase.toPath(), file.getParentFile().toPath().resolve(file.getName()));

            InfoDialog.show("Ваш файл успешно сохранен в " + file.getAbsolutePath());
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private static @NotNull FileChooser createFileChooser(String title, List<FileChooser.ExtensionFilter> extensionFilterList){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(extensionFilterList);

        return fileChooser;
    }

    public static @Nullable File openFileDialog(String title, Stage stage, List<FileChooser.ExtensionFilter> extensionFilterList){
        return createFileChooser(title, extensionFilterList).showOpenDialog(stage);
    }

    public static @Nullable File openSaveDialog(String title, Stage stage, List<FileChooser.ExtensionFilter> extensionFilterList){
        return createFileChooser(title, extensionFilterList).showSaveDialog(stage);
    }

    public static void applyBackground(Pane pane, String path, double width, double height){
        try {
            BackgroundImage backgroundImage = new BackgroundImage(Utils.getImage(path),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT, new BackgroundSize(width, height, false, false, true, true));
            pane.setBackground(new Background(backgroundImage));
        } catch (Exception e) {
            ErrorDialog.show(e);
        }
    }

    public static boolean checkPhoneNumber(@NotNull String phoneNumber){
        if(phoneNumber.length() < 10){
            ErrorDialog.show(new Exception("Введите корректный номер телефона. Длина телефона меньше 10"));
            return false;
        }

        if(!phoneNumber.contains("+")){
            ErrorDialog.show(new Exception("Введите корректный номер телефона. Номер не содержит +"));
            return false;
        }

        for (char c : phoneNumber.toCharArray()) {
            if(!Character.isDigit(c) & c != '+'){
                ErrorDialog.show(new Exception("Введите корректный номер телефона. Неподдерживаемые символы"));
                return false;
            }
        }

        return true;
    }

    public static boolean paneHasObjectWithId(String id, @NotNull Pane pane){
        boolean isFound = false;

        for (Node child : pane.getChildren()) {
            if(child.getId() != null) {
                if (child.getId().equals(id)) {
                    isFound = true;
                    break;
                }
            }
        }

        return isFound;
    }

    public static @Nullable Node findNodeWithIdInPane(String id, @NotNull Pane pane){
        if(!paneHasObjectWithId(id, pane))
            return null;

        for (Node child : pane.getChildren()) {
            if(child.getId() != null) {
                if (child.getId().equals(id))
                    return child;
            }
        }

        return null;
    }

    public static void openEditingScene(com.ds.tss.records.Record record, @NotNull RecordsTypes recordsTypes, IOnAction onClose){
        EditDataController editDataController = (EditDataController) AnotherScenes.goToAnotherScene("edit-data-view.fxml", "Редактирование данных");

        assert editDataController != null;
        editDataController.setOnClose(onClose);

        switch (recordsTypes){
            case CLIENT -> editDataController.loadClient((ClientRecord) record);
            case BRANCH -> editDataController.loadBranch((BranchRecord) record);
            case SERVICE -> editDataController.loadService((ServicesRecord) record);
            case STAFF -> editDataController.loadStaff((StaffRecord) record);
            case MAINTAINING -> editDataController.loadMaintaining((MaintainingRecord) record);
        }
    }
}
