package ap.projects.scraper;

import ap.projects.scraper.fetcher.HtmlFetcher;
import ap.projects.scraper.parser.HtmlParser;
import ap.projects.scraper.utils.FileTools;
import ap.projects.scraper.fetcher.ImageDownloader;
import ap.projects.scraper.Conf;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DomainHtmlScraper {
    private String domainAddress;
    private String baseDomain;
    private Queue<String> queue;
    private String saveBasePath;
    private ExecutorService executor;

    public DomainHtmlScraper(String domainAddress, String savePath) {
        this.domainAddress = domainAddress;
        this.queue = new LinkedList<>();
        this.saveBasePath = savePath;
        this.baseDomain = getBaseDomain(domainAddress);
        if (Conf.THREAD_COUNT > 0) {
            executor = Executors.newFixedThreadPool(Conf.THREAD_COUNT);
        }
    }

    public void start() throws IOException {
        Set<String> visited = Collections.synchronizedSet(new HashSet<>());
        queue.add(domainAddress);

        ExecutorService executor = Conf.THREAD_COUNT > 0
                ? Executors.newFixedThreadPool(Conf.THREAD_COUNT)
                : null;

        while (!queue.isEmpty()) {
            String url = queue.remove();

            if (!visited.contains(url) && isSameDomainOrSubdomain(url)) {
                if (executor != null) {
                    executor.submit(new FetchTask(url, visited, this));
                } else {
                    fetchAndSave(url, visited); // حالت تک‌نخی
                }
            }
        }

        if (executor != null) {
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                System.err.println("⚠️ Thread execution interrupted.");
            }
        }

        System.out.println("Operation was completed!");
    }

            public void fetchAndSave (String url, Set < String > visited){
                try {
                    Thread.sleep(Conf.DOWNLOAD_DELAY * 1000);

                    List<String> htmlLines = HtmlFetcher.fetchHtml(url);
                    String htmlSavePath = getSavePathForUrl(url);
                    FileTools.createParentDirectories(htmlSavePath);
                    FileTools.writeToFile(htmlSavePath, htmlLines);


                    List<String> imageUrls = HtmlParser.extractImageUrls(htmlLines);
                    for (String imgUrl : imageUrls) {
                        if (imgUrl.startsWith(domainAddress)) {
                            String filename = FileTools.extractFilename(imgUrl);
                            ImageDownloader.downloadImage(imgUrl, Conf.SAVE_DIRECTORY + "/image/" + filename);
                        }
                    }

                    List<String> audioUrls = HtmlParser.extractAudioUrls(htmlLines);
                    for (String audioUrl : audioUrls) {
                        if (audioUrl.startsWith(domainAddress)) {
                            String filename = FileTools.extractFilename(audioUrl);
                            ImageDownloader.downloadImage(audioUrl, Conf.SAVE_DIRECTORY + "/song/" + filename);
                        }
                    }

                    visited.add(url);
                    System.out.println("Saved successfully! " + url);

                    List<String> urls = HtmlParser.getAllUrlsFromList(htmlLines);
                    for (String u : urls) {
                        if (!visited.contains(u) && isSameDomainOrSubdomain(u)) {
                            queue.add(u);
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Error in downloading! " + url + " -> " + e.getMessage());
                }
            }

            private boolean isSameDomainOrSubdomain (String urlStr){
                try {
                    URL url = new URL(urlStr);
                    String host = url.getHost();
                    return host.endsWith(baseDomain);
                } catch (Exception e) {
                    return false;
                }
            }

            private String getBaseDomain (String fullUrl){
                try {
                    URL url = new URL(fullUrl);
                    return url.getHost();
                } catch (Exception e) {
                    return "";
                }
            }

            private String getSavePathForUrl (String urlStr){
                try {
                    URL url = new URL(urlStr);
                    String host = url.getHost();
                    String path = url.getPath();
                    if (path.endsWith("/")) {
                        path += "index.html";

                    }
                    if (!host.equals(baseDomain) && host.endsWith(baseDomain)) {
                        String subdomain = host.replace("." + baseDomain, "");
                        return saveBasePath + "/html/_" + subdomain + path;
                    } else {
                        return saveBasePath + "/html" + path;
                    }
                } catch (Exception e) {
                    return saveBasePath + "/html/error_path.html";
                }
            }
        }