module com.mozaik {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;

    opens com.mozaik to javafx.fxml, com.opencsv;
    exports com.mozaik;
}
