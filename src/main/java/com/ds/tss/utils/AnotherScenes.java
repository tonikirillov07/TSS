package com.ds.tss.utils;

import com.ds.tss.Main;
import com.ds.tss.dialogs.ErrorDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

public final class AnotherScenes {
    public static @Nullable Object goToAnotherScene(String path, String name){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource(path));
            fxmlLoader.load();

            Parent root = fxmlLoader.getRoot();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(name);
            stage.setResizable(true);

            stage.show();

            return fxmlLoader.getController();
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }
}
