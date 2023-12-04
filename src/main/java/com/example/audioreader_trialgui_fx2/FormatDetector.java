package com.example.audioreader_trialgui_fx2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FormatDetector {
    public static String getFormat(File file) throws IOException {
        // A byte array to store the first four bytes of the file
        byte[] header = new byte[4];

        // A FileInputStream to read the file
        FileInputStream fis = new FileInputStream(file);

        // Read the first four bytes of the file
        fis.read(header);

        // Close the FileInputStream
        fis.close();

        // Convert the byte array to a hexadecimal string
        StringBuilder sb = new StringBuilder();
        for (byte b : header) {
            sb.append(String.format("%02x", b));
        }
        String hex = sb.toString();

        // Check the hexadecimal string against known formats
        if (hex.startsWith("52494646")) { // RIFF
            return "wav";
        } else if (hex.startsWith("494433") || hex.startsWith("fff")) { // ID3
            return "mp3";
        } else if (hex.startsWith("4f676753")) { // OggS
            return "ogg";
        } else if (hex.startsWith("664c6143")) { // fLaC
            return "flac";
        } else {
            return "Unknown format";
        }
    }
}

