// This is the controller class for the main window
package com.example.audioreader_trialgui_fx2;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem openItem;
    @FXML
    private MenuItem playItem;
    @FXML
    private MenuItem displayHeaderItem;

    private File audioFile; // the selected audio file

    @FXML
    protected void handleOpen() {
        // create a file chooser to select an audio file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Audio File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.aac", "*.flac", "*.mp3", "*.ogg", "*.wav", "*.wma")
        );
        // show the file chooser dialog and get the selected file
        audioFile = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (audioFile != null) {
            // create an audio player object based on the file extension
            String extension = audioFile.getName().substring(audioFile.getName().lastIndexOf(".") + 1);

            // enable the play and display header menu items
            playItem.setDisable(false);
            displayHeaderItem.setDisable(false);
        }
    }

    @FXML
    protected void handlePlay() {
        try {
            // load the play pane fxml file
            FXMLLoader loader = new FXMLLoader(HelloController.class.getResource("play-pane.fxml"));
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
            FXMLLoader loader = new FXMLLoader(HelloController.class.getResource("display-header-pane.fxml"));
            AnchorPane displayRoot = loader.load();

            DisplayHeaderPaneController displayController = loader.getController();
            displayController.readHeader(audioFile);

            // create a new scene and stage for the display header pane
            Scene displayScene = new Scene(displayRoot, 600, 400);
            Stage displayStage = new Stage();
            displayStage.setScene(displayScene);
            displayStage.setTitle("Display Header");
            displayStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
