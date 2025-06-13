package ap.projects.scraper;

import java.util.Set;

public class FetchTask implements Runnable {
    private String url;
    private Set<String> visited;
    private DomainHtmlScraper scraper;

    public FetchTask(String url, Set<String> visited, DomainHtmlScraper scraper) {
        this.url = url;
        this.visited = visited;
        this.scraper = scraper;
    }

    @Override
    public void run() {
        scraper.fetchAndSave(url, visited);
    }
}
