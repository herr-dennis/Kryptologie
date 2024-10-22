module com.example.kryptologie {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens controll to javafx.fxml;
    opens model to javafx.fxml;
    exports controll;
    exports model;

}