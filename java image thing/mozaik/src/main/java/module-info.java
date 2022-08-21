module com.mozaik {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mozaik to javafx.fxml;
    exports com.mozaik;
}
