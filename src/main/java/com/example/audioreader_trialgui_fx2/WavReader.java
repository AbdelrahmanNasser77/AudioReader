package com.example.audioreader_trialgui_fx2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class WavReader {

    // Declare the fields to store the header information
    private String chunkID;
    private int chunkSize;
    private String format;
    private String subchunk1ID;
    private int subchunk1Size;
    private short audioFormat;
    private short numChannels;
    private int sampleRate;
    private int byteRate;
    private short blockAlign;
    private short bitsPerSample;
    private String subchunk2ID;
    private int subchunk2Size;

    public WavReader(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);

        // store the first 44 bytes of the file
        byte[] header = new byte[44];

        // Read the first 44 bytes
        fis.read(header);

        fis.close();

        // Convert the byte array to a ByteBuffer object
        ByteBuffer byteBuffer = ByteBuffer.wrap(header);

        // Set the byte order to little endian
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        // Create a byte array to store the first four bytes
        byte[] ChunkIDBytes = new byte[4];

        // Copy the first four bytes from the ByteBuffer to the byte array
        byteBuffer.get(ChunkIDBytes);

        // Get the first four bytes as a string
        chunkID = new String(ChunkIDBytes);

        // Get the next four bytes as an int
        chunkSize = byteBuffer.getInt();

        // Create a byte array to store the next four bytes
        byte[] formatBytes = new byte[4];

        // Copy the next four bytes from the ByteBuffer to the byte array
        byteBuffer.get(formatBytes);

        // Get the next four bytes as a string
        format = new String(formatBytes);

        // Create a byte array to store the next four bytes
        byte[] fmtBytes = new byte[4];

        // Copy the next four bytes from the ByteBuffer to the byte array
        byteBuffer.get(fmtBytes);

        // Get the next four bytes as a string
        subchunk1ID = new String(fmtBytes);

        // Get the next four bytes as an int
        subchunk1Size = byteBuffer.getInt();

        // Get the next two bytes as a short
        audioFormat = byteBuffer.getShort();

        // Get the next two bytes as a short
        numChannels = byteBuffer.getShort();

        // Get the next four bytes as an int
        sampleRate = byteBuffer.getInt();

        // Get the next four bytes as an int
        byteRate = byteBuffer.getInt();

        // Get the next two bytes as a short
        blockAlign = byteBuffer.getShort();

        // Get the next two bytes as a short
        bitsPerSample = byteBuffer.getShort();

        // Create a byte array to store the next four bytes
        byte[] dataBytes = new byte[4];

        // Copy the next four bytes from the ByteBuffer to the byte array
        byteBuffer.get(dataBytes);

        // Get the next four bytes as a string
        subchunk2ID = new String(dataBytes);

        // Get the next four bytes as an int
        subchunk2Size = byteBuffer.getInt();
    }

    // Getter methods for the header information
    public String getChunkID() {
        return chunkID;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public String getFormat() {
        return format;
    }

    public String getSubchunk1ID() {
        return subchunk1ID;
    }

    public int getSubchunk1Size() {
        return subchunk1Size;
    }

    public short getFmtCode() {
        return audioFormat;
    }

    public short getNumChannels() {
        return numChannels;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public int getByteRate() {
        return byteRate;
    }

    public short getBlockAlign() {
        return blockAlign;
    }

    public short getBitsPerSample() {
        return bitsPerSample;
    }

    public String getSubchunk2ID() {
        return subchunk2ID;
    }

    public int getSubchunk2Size() {
        return subchunk2Size;
    }
}