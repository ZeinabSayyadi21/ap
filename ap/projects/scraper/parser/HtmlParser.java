package ap.projects.scraper.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;

public class HtmlParser {

    public static String getFirstUrl(String htmlLine) {
        String url = null;
        int startIndex = htmlLine.indexOf("href=\"");
        if (startIndex >= 0) {
            try {
                int hrefLength = "href\"".length();
                int endIndex = htmlLine.indexOf("\"", startIndex + hrefLength + 1);
                url = htmlLine.substring(startIndex + hrefLength + 1, endIndex);
            } catch (Exception e) {
            }
        }
        return url;
    }

    private static List<String> getAllUrlsFromHtmlLinesStream(Stream<String> htmlLinesStream) throws IOException {
        List<String> urls = htmlLinesStream
                .map(line -> getFirstUrl(line))
                .filter(s -> s != null)
                .collect(Collectors.toList());
        return urls;
    }

    public static List<String> getAllUrlsFromFile(String filePath) throws IOException {
        return getAllUrlsFromHtmlLinesStream(Files.lines(Paths.get(filePath)));
    }

    public static List<String> getAllUrlsFromList(List<String> htmlLines) throws IOException {
        return getAllUrlsFromHtmlLinesStream(htmlLines.stream());
    }

    public static String getFirstImageUrl(String htmlLine) {
        String url = null;
        int startIndex = htmlLine.indexOf("img src=\"");
        if (startIndex >= 0) {
            try {
                int prefixLength = "img src=\"".length();
                int endIndex = htmlLine.indexOf("\"" , startIndex + prefixLength);
                if (endIndex > startIndex) {
                    url = htmlLine.substring(startIndex + prefixLength , endIndex);
                }
            } catch (Exception e) {

            }
        }
        return url;
    }

    public static List<String> extractImageUrls(List<String> htmlLines) {
        List<String> imageUrls = new ArrayList<>();
        for (String line : htmlLines) {
            int index = line.indexOf("<img");
            while (index >= 0) {
                int srcIndex = line.indexOf("src=\"", index);
                if (srcIndex >= 0) {
                    int start = srcIndex + 5;
                    int end = line.indexOf("\"", start);
                    if (end > start) {
                        String url = line.substring(start, end);
                        imageUrls.add(url);
                    }
                    index = line.indexOf("<img", end);
                } else break;
            }
        }
        return imageUrls;
    }

    public static List<String> extractAudioUrls(List<String> htmlLines) {
        List<String> audioUrls = new ArrayList<>();
        for (String line : htmlLines) {
            int index = line.indexOf("<audio");
            while (index >= 0) {
                int srcIndex = line.indexOf("src=\"", index);
                if (srcIndex >= 0) {
                    int start = srcIndex + 5;
                    int end = line.indexOf("\"", start);
                    if (end > start) {
                        String url = line.substring(start, end);
                        audioUrls.add(url);
                    }
                    index = line.indexOf("<audio", end);
                } else break;
            }
        }
        return audioUrls;
    }


}

