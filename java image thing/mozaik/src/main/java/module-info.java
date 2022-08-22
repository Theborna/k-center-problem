module com.mozaik {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires json.simple;
    requires java.desktop;

    opens com.mozaik to javafx.fxml, com.opencsv, json.simple, java.desktop;
    exports com.mozaik;
}
