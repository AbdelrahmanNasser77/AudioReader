module com.example.audioreader {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.audioreader_trialgui_fx2 to javafx.fxml;
    exports com.example.audioreader_trialgui_fx2;
}