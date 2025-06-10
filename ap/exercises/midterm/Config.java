package ap.exercises.midterm;

import java.io.*;
import java.util.*;

public class Config {
    private String storageType;

    public Config(String filePath) {
        loadConfig(filePath);
    }

    private void loadConfig(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("storage=")) {
                    storageType = line.split("=")[1].trim();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading config file.");
        }
    }

    public String getStorageType() {
        return storageType;
    }
}