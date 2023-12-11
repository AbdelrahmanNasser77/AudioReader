package com.example.audioreader_trialgui_fx2;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;

import javafx.util.Duration;

import java.text.DecimalFormat;

public class PlayPaneController {
    @FXML
    private Button playButton;

    @FXML
    private Button stopButton;

    @FXML
    private Label timeLabel;

    @FXML
    private Slider progressBar;

    @FXML
    private Slider volumeBar;

    private MediaPlayer audioPlayer;

    private DecimalFormat timeFormat;


    public void initialize() {
        timeFormat = new DecimalFormat("00");

        stopButton.setDisable(true);

        playButton.setOnAction(event -> playAudio());
        stopButton.setOnAction(event -> stopAudio());

        progressBar.setOnMousePressed(event -> pauseAudio());
        progressBar.setOnMouseReleased(event -> seekAudio());
        volumeBar.valueProperty().addListener(event -> adjustVolume());
    }

    public void setAudioPlayer(MediaPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;

        volumeBar.setValue(audioPlayer.getVolume() * 100);

        Duration duration = audioPlayer.getMedia().getDuration();

        timeLabel.setText("0:00/" + formatTime(duration));


        audioPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            timeLabel.setText(formatTime(newValue));
            progressBar.setValue(newValue.toSeconds());

        });

        // add a change listener to the total duration property
        audioPlayer.totalDurationProperty().addListener((obs, oldDuration, newDuration) -> {
            progressBar.setMax(newDuration.toSeconds());
        });

    }


    private void playAudio() {
        stopButton.setDisable(false);

        playButton.setText(switch (playButton.getText()) {
            case "Play" -> {

                audioPlayer.play();
                yield "Pause";
            }
            default -> {
                audioPlayer.pause();
                yield "Play";
            }
        });
    }

    private void pauseAudio() {
        // pause the audio
        audioPlayer.pause();
    }

    private void stopAudio() {
        playButton.setDisable(false);
        stopButton.setDisable(true);

        audioPlayer.stop();
        playButton.setText("Play");
    }

    private void seekAudio() {
        audioPlayer.seek(Duration.seconds(progressBar.getValue()));
        audioPlayer.play();
    }

    private void adjustVolume() {
        audioPlayer.setVolume(volumeBar.getValue() / 100);
    }

    private String formatTime(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) duration.toSeconds() % 60;

        // get the total time of the file
        Duration total = audioPlayer.getMedia().getDuration();
        int totalMinutes = (int) total.toMinutes();
        int totalSeconds = (int) total.toSeconds() % 60;

        return timeFormat.format(minutes) + ":" + timeFormat.format(seconds) + "/" + timeFormat.format(totalMinutes) + ":" + timeFormat.format(totalSeconds);
    }
}
