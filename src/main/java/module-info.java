module com.marcelhomsak.tabsy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.marcelhomsak.tabsy to javafx.fxml;
    exports com.marcelhomsak.tabsy;
}