module com.example.audioreader_trialgui_fx2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.media;

    opens com.example.audioreader_trialgui_fx2 to javafx.fxml;
    exports com.example.audioreader_trialgui_fx2;
}