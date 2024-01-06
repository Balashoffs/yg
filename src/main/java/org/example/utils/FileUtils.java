package org.example.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static void saveToFile(String json, String... paths) {
        try {
            Path rootPath = Path.of(paths[0]);
            if (!Files.exists(rootPath)) {
                Files.createDirectory(rootPath);
            }
            Path fullPath = Path.of(String.join("/", paths));
            Files.writeString(fullPath, json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFile(String... paths) {
        try {
            Path rootPath = Path.of(paths[0]);
            if (!Files.exists(rootPath)) {
                Files.createDirectory(rootPath);
            }
            Path fullPath = Path.of(String.join("/", paths));
            return Files.readString(fullPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
