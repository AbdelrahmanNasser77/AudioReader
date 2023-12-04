package com.example.audioreader_trialgui_fx2;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Mp3Reader {
    private int mpegVersion;
    private int layer;
    private int protectionBit;
    private int bitrate;
    private int sampleRate;
    private int paddingBit;
    private int privateBit;
    private int channelMode;
    private int modeExtension;
    private int copyRight;
    private int original;
    private int emphasis;

    public Mp3Reader(File file) throws IOException {
        DataInputStream input = null;

        try {
            input = new DataInputStream(new FileInputStream(file));

            // The byte value to store the current byte read
            int byteValue;

            // The boolean flag to indicate if the frame sync is found
            boolean syncFound = false;

            int header;

            // The arrays to store the bitrate and sampling frequency values for each version and layer
            int[][][] bitrates = {
                    // For MPEG 2.5
                    {
                            // Reserved
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            // For Layer III
                            {0, 8, 16, 24, 32, 40, 48, 56, 64, 80, 96, 112, 128, 144, 160},
                            // For Layer II
                            {0, 8, 16, 24, 32, 40, 48, 56, 64, 80, 96, 112, 128, 144, 160},
                            // For Layer I
                            {0, 32, 48, 56, 64, 80, 96, 112, 128, 144, 160, 176, 192, 224, 256}
                    },
                    // Reserved
                    {
                            // Reserved
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            // Reserved
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            // Reserved
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            // Reserved
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                    },
                    // For MPEG 2
                    {
                            // Reserved
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            // For Layer III
                            {0, 8, 16, 24, 32, 40, 48, 56, 64, 80, 96, 112, 128, 144, 160},
                            // For Layer II
                            {0, 8, 16, 24, 32, 40, 48, 56, 64, 80, 96, 112, 128, 144, 160},
                            // For Layer I
                            {0, 32, 48, 56, 64, 80, 96, 112, 128, 144, 160, 176, 192, 224, 256}
                    },
                    // For MPEG 1
                    {
                            // Reserved
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                            // For Layer III
                            {0, 32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320},
                            // For Layer II
                            {0, 32, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320, 384},
                            // For Layer I
                            {0, 32, 64, 96, 128, 160, 192, 224, 256, 288, 320, 352, 384, 416, 448}
                    }
            };

            int[][] samplingFrequencies = {
                    // For MPEG 2.5
                    {11025, 12000, 8000, 0},
                    // Reserved
                    {0, 0, 0, 0},
                    // For MPEG 2
                    {22050, 24000, 16000, 0},
                    // For MPEG 1
                    {44100, 48000, 32000, 0}
            };

            // Loop until the end of the file or the frame sync is found
            while (!syncFound && (byteValue = input.read()) != -1) {
                // Check if the current byte is 255, which means it could be the start of the frame sync
                if (byteValue == 255) {
                    // Read the next byte
                    byteValue = input.read();

                    // Check if the next byte is not -1 (end of file) and the most significant 3 bits are 1, which means it is the end of the frame sync
                    if (byteValue != -1 && (byteValue & 224) == 224) {
                        // Set the sync found flag to true
                        syncFound = true;

                        // Shift the first byte 8 bits to the left and combine it with the second byte to get the first 16 bits

                        header = (255 << 8) | byteValue;

                        // Read the next 2 bytes and combine them with the first 16 bits to get the full 32-bit header
                        header = (header << 16) | input.readUnsignedShort();

                        // Extract the version from the header
                        mpegVersion = (header >> 19) & 3;

                        // Extract the layer from the header
                        layer = (header >> 17) & 3;

                        // Extract the error protection from the header
                        protectionBit = (header >> 16) & 1;

                        // Extract the bitrate index from the header
                        int bitrateIndex = (header >> 12) & 15;

                        // Extract the sampling frequency index from the header
                        int samplingFrequencyIndex = (header >> 10) & 3;

                        // Extract the padding bit from the header
                        paddingBit = (header >> 9) & 1;

                        // Extract the private bit from the header
                        privateBit = (header >> 8) & 1;

                        // Extract the channel mode from the header
                        channelMode = (header >> 6) & 3 ^ 3;

                        // Extract the mode extension from the header
                        modeExtension = (header >> 4) & 3;

                        // Extract the emphasis from the header
                        emphasis = header & 3;

                        // Look up the actual bitrate from the table, using the mpegVersion, layer, and bitrate index
                        bitrate = bitrates[mpegVersion][layer][bitrateIndex];


                        // Extract the original bit from the header
                        original = (header >> 2) & 1;

                        // Extract the copyright bit from the header
                        copyRight = (header >> 3) & 1;

                        // Look up the actual bitrate from the table, using the mpegVersion, layer, and bitrate index
                        bitrate = bitrates[mpegVersion][layer][bitrateIndex];

                        // Look up the actual sampling frequency from the table, using the mpegVersion and sampling frequency index
                        sampleRate = samplingFrequencies[mpegVersion][samplingFrequencyIndex];
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getMpegVersion() {
        return mpegVersion;
    }

    public int getLayer() {
        return layer;
    }

    public int getProtectionBit() {
        return protectionBit;
    }

    public int getBitrate() {
        return bitrate;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public int getPaddingBit() {
        return paddingBit;
    }

    public int getPrivateBit() {
        return privateBit;
    }

    public int getChannelMode() {
        return channelMode;
    }

    public String getChannelModeName(){
        String channelModeName = null;
        switch (getChannelMode()){
            case 0:
                channelModeName = "Single channel (Mono)";
                break;
            case 1:
                channelModeName = "Dual channel (2 mono channels)";
                break;
            case 2:
                channelModeName = "Joint stereo (Stereo)";
                break;
            case 3:
                channelModeName = "Stereo";
                break;
        }
        return channelModeName;
    }

    public int getModeExtension() {
        return modeExtension;
    }

    public int getCopyRight() {
        return copyRight;
    }

    public int getOriginal() {
        return original;
    }

    public int getEmphasis() {
        return emphasis;
    }
}
