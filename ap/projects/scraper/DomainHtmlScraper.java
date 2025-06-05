package ap.projects.scraper;

import ap.projects.scraper.fetcher.HtmlFetcher;
import ap.projects.scraper.parser.HtmlParser;
import ap.projects.scraper.store.HtmlFileManager;
import ap.projects.scraper.utils.FileTools;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DomainHtmlScraper {

    private String domainAddress;
    private String baseDomain;
    private Queue<String> queue;
    private HtmlFileManager htmlFileManager;

    public DomainHtmlScraper(String domainAddress, String savePath) {
        this.domainAddress = domainAddress;
        this.queue = new LinkedList<>();
        this.htmlFileManager = new HtmlFileManager(savePath);
        this.baseDomain = getBaseDomain(domainAddress);
    }

    public void start() throws IOException {
        Set<String> visited = new HashSet<>();

        fetchAndSave(domainAddress, visited);

        while (!queue.isEmpty()) {
            String url = queue.remove();

            if (!visited.contains(url) && isSameDomainOrSubdomain(url)) {
                fetchAndSave(url, visited);
            }
        }

        System.out.println("The operation was completed!");
    }

    private void fetchAndSave(String url, Set<String> visited) {
        try {
            List<String> htmlLines = HtmlFetcher.fetchHtml(url);
            String savePath = getSavePathForUrl(url);
            FileTools.createParentDirectories(savePath);
            FileTools.writeToFile(savePath, htmlLines);

            visited.add(url);
            System.out.println("Saved successfully! " + url);

            List<String> urls = HtmlParser.getAllUrlsFromList(htmlLines);
            for (String u : urls) {
                if (!visited.contains(u) && isSameDomainOrSubdomain(u)) {
                    queue.add(u);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in downloading: " + url + ": " + e.getMessage());
        }
    }

    private boolean isSameDomainOrSubdomain(String urlStr) {
        try {
            URL url = new URL(urlStr);
            String host = url.getHost();
            return host.endsWith(baseDomain);
        } catch (Exception e) {
            return false;
        }
    }

    private String getBaseDomain(String fullUrl) {
        try {
            URL url = new URL(fullUrl);
            return url.getHost();
        } catch (Exception e) {
            return "";
        }
    }

    private String getSavePathForUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            String host = url.getHost();
            String path = url.getPath();

            if (path.endsWith("/")) {
                path += "index.html";
            }

            String base = Conf.SAVE_DIRECTORY;

            if (!host.equals(baseDomain) && host.endsWith(baseDomain)) {

                String subdomain = host.replace("." + baseDomain, "");
                return base + "/_" + subdomain + path;
            } else {
                return base + path;
            }
        } catch (Exception e) {
            return Conf.SAVE_DIRECTORY + "/error_path.html";
        }
    }
}
