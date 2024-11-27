module com.kamilglazer.to_lab03 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kamilglazer.to_lab03 to javafx.fxml;
    exports com.kamilglazer.to_lab03;
}