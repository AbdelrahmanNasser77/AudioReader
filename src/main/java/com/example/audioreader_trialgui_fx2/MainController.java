package com.example.audioreader_trialgui_fx2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainController {

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem playItem;

    @FXML
    private MenuItem displayHeaderItem;

    private File audioFile;

    @FXML
    protected void handleOpen() throws IOException {
        // create a file chooser to select an audio file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Audio File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.aac", "*.flac", "*.mp3", "*.ogg", "*.wav", "*.wma")
        );
        // show the file chooser dialog and get the selected file
        audioFile = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (audioFile != null) {
            String format = FormatDetector.getFormat(audioFile);

            if (format.equals("wav") || format.equals("mp3")) {
                playItem.setDisable(false);
                displayHeaderItem.setDisable(false);
            }
            else {
                Alert alert = new Alert (Alert.AlertType.WARNING);

                alert.setTitle ("WARNING");
                alert.setHeaderText ("UNSUPPORTED FORMAT");
                alert.setContentText ("Your File Format is not Supported. Try Another File!");

                alert.showAndWait ();
            }
        }
    }

    @FXML
    protected void handlePlay() {
        try {
            // load the play pane fxml file
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("play-pane.fxml"));
            AnchorPane playRoot = loader.load();

            // get the play pane controller and pass the audio player object
            PlayPaneController playController = loader.getController();

            Media media = new Media(audioFile.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            playController.setAudioPlayer(mediaPlayer);

            // create a new scene and stage for the play pane
            Scene playScene = new Scene(playRoot, 300, 200);
            Stage playStage = new Stage();
            playStage.setScene(playScene);
            String title = "Play" + "\"" + audioFile.getName() + "\"";
            playStage.setTitle(title);
            playStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleDisplayHeader() {
        try {
            // load the display header pane fxml file
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("display-header-pane.fxml"));
            AnchorPane displayRoot = loader.load();

            DisplayHeaderPaneController displayController = loader.getController();
            displayController.readHeader(audioFile);

            // create a new scene and stage for the display header pane
            Scene displayScene = new Scene(displayRoot, 600, 400);
            Stage displayStage = new Stage();
            displayStage.setScene(displayScene);
            displayStage.setTitle("Header of \"" + audioFile.getName() + "\"");
            displayStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
