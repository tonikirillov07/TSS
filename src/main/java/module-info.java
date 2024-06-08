module com.ds.tss {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.desktop;
    requires java.sql;

    opens com.ds.tss.controllers to javafx.fxml;
    opens com.ds.tss to javafx.fxml;
    exports com.ds.tss;
}