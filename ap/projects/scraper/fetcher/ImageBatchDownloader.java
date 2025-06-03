package ap.projects.scraper.fetcher;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageBatchDownloader {


        public static void downloadAllImages(List<String> imageUrls, String downloadFolder) {
            int count = 1;
            for (String imageUrl : imageUrls) {
                try {
                    String fileName = "image_" + count + getFileExtension(imageUrl);
                    File file = new File(downloadFolder + "/" + fileName);

                    if (file.exists()) {
                        System.out.println("The file has already been downloaded! " + fileName);
                        continue;
                    }

                    ImageDownloader.downloadImage(imageUrl, file.getPath());
                    System.out.println("Downloaded successfully! " + imageUrl);
                    count++;

                } catch (Exception e) {
                    System.err.println("Error in downloading! " + imageUrl);
                }
            }
        }

        private static String getFileExtension(String url) {
            try {
                int dotIndex = url.lastIndexOf('.');
                if (dotIndex > 0 && dotIndex < url.length() - 1) {
                    return url.substring(dotIndex);
                }
            } catch (Exception e) {
            }
            return ".jpg";
        }
    }
