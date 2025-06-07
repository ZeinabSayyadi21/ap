package ap.projects.scraper.utils;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileTools {

    public static List<String> getTextFileLines(String filePath){
        try {
            return Files.lines(Paths.get(filePath))
                    .collect(Collectors.toList());
        } catch (IOException e) {
//            throw new RuntimeException(e);
            return null;
        }
    }

    public static void createParentDirectories(String filePath) {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    public static void writeToFile(String filePath, List<String> lines) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static String extractFilename(String url) {
        try {
            return url.substring(url.lastIndexOf("/") + 1).split("\\?")[0];
        } catch (Exception e) {
            return "unknown_file";
        }
    }
}
