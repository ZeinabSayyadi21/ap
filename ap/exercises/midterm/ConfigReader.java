package ap.exercises.midterm;

import java.io.File;
import java.util.Scanner;

public class ConfigReader {
    public static StorageType getStorageType() {
        File file = new File("config.txt");
        if (!file.exists()) return StorageType.TABSPLIT;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim().toLowerCase();
                if (line.startsWith("storage=")) {
                    String type = line.substring(8).trim();
                    switch (type) {
                        case "json" :
                            return StorageType.JSON;
                        case "sqlite" :
                            return StorageType.SQLITE;
                        default :
                            return StorageType.TABSPLIT;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading config. Defaulting to tab-split.");
        }
        return StorageType.TABSPLIT;
    }

    public enum StorageType {
        TABSPLIT, JSON, SQLITE
    }
}
