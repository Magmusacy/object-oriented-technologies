package app;

import java.io.IOException;
import java.util.List;

public class CrawlerApp {

    public static final String GOOGLE_CUSTOM_SEARCH_API_KEY = System.getenv("GOOGLE_CUSTOM_SEARCH_API_KEY");

    private static final List<String> TOPICS = List.of("Agent Cooper", "Sherlock", "Poirot", "Detective Monk", "Scooby Doo", "Herobrine minecraft");

    public static void main(String[] args) throws IOException {
        PhotoCrawler photoCrawler = new PhotoCrawler();
        photoCrawler.resetLibrary();
//        photoCrawler.downloadPhotoExamples();
        photoCrawler.downloadPhotosForQuery(TOPICS.get(0));
//        photoCrawler.downloadPhotosForMultipleQueries(TOPICS);
    }
}