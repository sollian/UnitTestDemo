package com.example.unittest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author shouxianli on 2020/7/16.
 */
public class FileUtil {

    public static void writeStringToFile(File file, String message) {
        try (FileWriter writer = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(message, 0, message.length());
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFileToString(File file) {
        BufferedReader bufferedReader = null;
        try {
            FileReader reader = new FileReader(file);
            bufferedReader = new BufferedReader(reader);
            return bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
