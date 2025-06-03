package ap.projects.scraper.abalyzer;

import ap.projects.scraper.Conf;
import ap.projects.scraper.parser.HtmlParser;
import ap.projects.scraper.utils.DirectoryTools;
import ap.projects.scraper.utils.FileTools;
import ap.projects.scraper.utils.ObjectCounter;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class HtmlAnalyzer {


        private static List<String> fileList = DirectoryTools.getFilesAbsolutePathInDirectory(Conf.SAVE_DIRECTORY);

        public static List<String> getAllUrls() {
            List<String> urls = fileList.stream()
                    .map(fileAddress -> FileTools.getTextFileLines(fileAddress))
                    .filter(s -> s != null)
                    .flatMap(s -> s.stream())
                    .map(s -> HtmlParser.getFirstUrl(s))
                    .filter(s -> s != null)
                    .filter(s -> s.length() > 1)
                    .collect(Collectors.toList());
            return urls;
        }

        public static List<String> getTopUrls(int k){
            Map<String, Long> urlCount = getAllUrls().stream()
                    .collect(Collectors.groupingBy(
                            s -> s,
                            Collectors.counting()
                    ));

            List<String> topUrls = urlCount.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(k)
                    .map(s -> s.getKey())
                    .collect(Collectors.toList());

            return topUrls;
        }
        public static void printTopCountUrls(int k){
            ObjectCounter<String> urlCounter=new ObjectCounter<>();
            getAllUrls().forEach(s -> urlCounter.add(s));
            for (Map.Entry<String, Integer> urlCountEntry : urlCounter.getTop(k)) {
                System.out.println(urlCountEntry.getKey()+" -> "+urlCountEntry.getValue());
            }
        }

        public static void main(String[] args) {

            HtmlAnalyzer.printTopCountUrls(10);

            System.out.println("____________________");
            HtmlAnalyzer.getTopUrls(10).forEach(s -> System.out.println(s));

        }

    public static List<String> getAllImageUrls() {
        List<String> imageUrls = fileList.stream()
                .map(FileTools::getTextFileLines)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .map(HtmlParser::getFirstImageUrl)
                .filter(Objects::nonNull)
                .filter(s -> s.length() > 1)
                .distinct()
                .collect(Collectors.toList());

        return imageUrls;
    }
    public static void saveImageUrlsToFile(String outputPath) {
        List<String> imageUrls = getAllImageUrls();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            for (String url : imageUrls) {
                writer.write(url);
                writer.newLine();
            }
            System.out.println("Saved links: " + outputPath);
        } catch (IOException e) {
            System.err.println("Error in saving links: " + e.getMessage());
        }
    }
    }
