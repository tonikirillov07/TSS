module com.ds.tss {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ds.tss to javafx.fxml;
    exports com.ds.tss;
}