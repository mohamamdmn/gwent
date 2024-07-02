module untitled2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.smartcardio;
    requires java.desktop;
    exports view;
    exports controller;
    exports model;
    exports enums;
    opens view to javafx.fxml;
}