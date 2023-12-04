package com.example.audioreader_trialgui_fx2;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;

public class DisplayHeaderPaneController {

    @FXML
    private TableView<AudioProperty> tableView;

    @FXML
    private TableColumn<AudioProperty, String> columnName;

    @FXML
    private TableColumn<AudioProperty, String> value;



    @FXML
    void readHeader(File file) throws IOException {
        tableView.getItems().clear();
        tableView.setVisible(true);

        tableView.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tableView.prefWidthProperty().bind(newValue.widthProperty());
                tableView.prefHeightProperty().bind(newValue.heightProperty());
            }
        });

        columnName.setCellValueFactory(new PropertyValueFactory<>("columnName"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));

        String format = FormatDetector.getFormat(file);
        if (format.equals("wav")) {
            WavReader wavReader = new WavReader(file);
            tableView.getItems().addAll(
                    new AudioProperty("ChunkID", wavReader.getChunkID()),
                    new AudioProperty("chunkSize", wavReader.getChunkSize()),
                    new AudioProperty("Format", wavReader.getFormat()),
                    new AudioProperty("Subchunk1ID", wavReader.getSubchunk1ID()),
                    new AudioProperty("Subchunk1Size:", wavReader.getSubchunk1Size()),
                    new AudioProperty("AudioFormat:", wavReader.getFmtCode()),
                    new AudioProperty("NumChannels", wavReader.getNumChannels()),
                    new AudioProperty("SampleRate", wavReader.getSampleRate()),
                    new AudioProperty("ByteRate", wavReader.getByteRate()),
                    new AudioProperty("blockAlign", wavReader.getBlockAlign()),
                    new AudioProperty("bitsPerSample", wavReader.getBitsPerSample()),
                    new AudioProperty("Subchunk2ID:", wavReader.getSubchunk2ID()),
                    new AudioProperty("Subchunk2Size:", wavReader.getSubchunk2Size())
            );
        } else if (format.equals("mp3")) {
            Mp3Reader mp3Reader = new Mp3Reader(file);
            tableView.getItems().addAll(
                    new AudioProperty("mpegVersion", mp3Reader.getMpegVersion()),
                    new AudioProperty("Layer", mp3Reader.getLayer()),
                    new AudioProperty("ProtectionBit", mp3Reader.getProtectionBit()),
                    new AudioProperty("Bitrate", mp3Reader.getBitrate()),
                    new AudioProperty("SampleRate", mp3Reader.getSampleRate()),
                    new AudioProperty("PaddingBit", mp3Reader.getPaddingBit()),
                    new AudioProperty("PrivateBit", mp3Reader.getPrivateBit()),
                    new AudioProperty("ChannelMode", mp3Reader.getChannelModeName()),
                    new AudioProperty("ModeExtension", mp3Reader.getModeExtension()),
                    new AudioProperty("Copyright", mp3Reader.getCopyRight()),
                    new AudioProperty("Original", mp3Reader.getOriginal()),
                    new AudioProperty("Emphasis" , mp3Reader.getEmphasis())
            );
        }

    }

}
