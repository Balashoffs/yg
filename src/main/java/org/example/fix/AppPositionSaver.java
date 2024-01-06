package org.example.fix;

import org.example.utils.JsonUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AppPositionSaver {
    final Path currentJsonPath =  Path.of("games", "current.json");
    public AppPosition getCurrentGameType() {
        try {
           String json =  Files.readString(currentJsonPath);
           return JsonUtils.fromJson(json, AppPosition.class);
        } catch (IOException e) {

        }
        return new AppPosition("", 0);
    }

    public void setNextGameType(String gameTypeTitle, int nextPos) {
        try {
            if (nextPos == -1) {
                Files.delete(currentJsonPath);
            }
            AppPosition appPosition = new AppPosition(gameTypeTitle, nextPos);
            String json = JsonUtils.toJson(appPosition, AppPosition.class);
            Files.writeString(currentJsonPath, json);
        } catch (IOException e) {

        }
    }
}


