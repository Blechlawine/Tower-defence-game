package de.marc.towerDefenceGame.utils;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.json.JSONString;
import org.json.JSONTokener;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static JSONObject readJSONFile(String fileName) {
        StringBuilder fileString = new StringBuilder();
        InputStream stream = getFileFromResourceAsStream(fileName);

        JSONTokener tokener = new JSONTokener(stream);
        return new JSONObject(tokener);
    }

    private static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if(inputStream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        } else {
            return inputStream;
        }
    }

}
