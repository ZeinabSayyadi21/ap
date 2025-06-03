package ap.projects.scraper;

import ap.projects.scraper.abalyzer.HtmlAnalyzer;
import ap.projects.scraper.fetcher.ImageDownloader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageUrlProcessor {


        public static void process(String outputFilePath) {
            try {
                List<String> imageUrls = HtmlAnalyzer.getAllImageUrls();

                Set<String> uniqueImageUrls = new HashSet<>(imageUrls);

                saveToFile(outputFilePath, uniqueImageUrls);

                downloadImages(uniqueImageUrls);

                System.out.println("Everything was done successfully!");

            } catch (Exception e) {
                System.err.println("Error processing links: " + e.getMessage());
            }
        }

        private static void saveToFile(String filePath, Set<String> urls) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String url : urls) {
                    writer.write(url);
                    writer.newLine();
                }
                System.out.println("Links saved in: " + filePath);
            } catch (IOException e) {
                System.err.println("Error saving file: " + e.getMessage());
            }
        }

        private static void downloadImages(Set<String> urls) {
            for (String url : urls) {
                try {
                    String filename = url.substring(url.lastIndexOf("/") + 1);
                    String imageSavePath = "fetched_images/" + filename;
                    ImageDownloader.downloadImage(url, imageSavePath);
                } catch (Exception e) {
                    System.err.println("Download failed for: " + url);
                }
            }
        }
    }

