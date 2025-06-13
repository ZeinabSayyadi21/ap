package ap.projects.scraper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Conf {

    public static final String DOMAIN_ADDRESS = "https://znu.ac.ir";

    public static final String SAVE_DIRECTORY = "fetched_html";

    public static int DOWNLOAD_DELAY = 2;

    public static int THREAD_COUNT = 0;

    static {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));
            THREAD_COUNT = Integer.parseInt(props.getProperty("thread-count", "0"));
            DOWNLOAD_DELAY = Integer.parseInt(props.getProperty("download-delay", "1"));
        } catch (IOException e) {
            System.err.println(":warning:️ config.properties not found. Using default config.");
        }
    }
}

