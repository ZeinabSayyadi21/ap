package ap.projects.scraper.store;

import ap.projects.scraper.utils.DirectoryTools;
import ap.projects.scraper.utils.FileTools;

import java.io.PrintWriter;
import java.util.List;

public class HtmlFileManager {

    private String saveFileBasePath;
    private static int saveCounter=0;

    public HtmlFileManager(String saveFileBasePath) {
//        this.saveFileBasePath = DirectoryTools.createDirectoryWithTimeStamp(saveFileBasePath);

        this.saveFileBasePath = saveFileBasePath;
        DirectoryTools.createDirectory(saveFileBasePath);
    }

    public void save(List<String> lines) {
        try {
            String saveHtmlFileAddress = getSaveHtmlFileAddress();
            PrintWriter out = new PrintWriter(saveHtmlFileAddress);
            for (String line : lines) {
                out.println(line);
            }
            out.close();

            System.out.println("save counter: " + saveCounter);
        }catch (Exception e){
            System.out.println("save failed: " + e.getMessage());
        }
    }

    public String getSaveHtmlFileAddress(){
        saveCounter++;
        return saveFileBasePath +"/"+ saveCounter;
    }

    public void saveWithUrl(String url, List<String> lines) {
        try {
            String filePath = getPathFromUrl(url);
            FileTools.createParentDirectories(filePath);
            FileTools.writeToFile(filePath, lines);
        } catch (Exception e) {
            System.out.println("❌ ذخیره HTML با URL شکست خورد: " + url + " -> " + e.getMessage());
        }
    }

    private String getPathFromUrl(String url) {
        String cleanUrl = url.replaceFirst("https?://", "");
        String[] parts = cleanUrl.split("/");

        StringBuilder sb = new StringBuilder(saveFileBasePath);
        sb.append("/html");

        if (parts.length == 0) return sb.append("/index.html").toString();

        if (parts.length >= 1) {
            String domainPart = parts[0];
            if (!domainPart.contains(".")) domainPart = "_unknown";
            else if (domainPart.split("\\.").length > 2) {
                String sub = domainPart.substring(0, domainPart.indexOf('.'));
                domainPart = "_" + sub;
            }
            sb.append("/").append(domainPart);
        }

        for (int i = 1; i < parts.length - 1; i++) {
            sb.append("/").append(parts[i]);
        }

        String fileName = parts[parts.length - 1];
        if (!fileName.endsWith(".html")) {
            fileName = fileName + ".html";
        }
        sb.append("/").append(fileName);

        return sb.toString();
    }
}

