package app.scraper;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PageScraper {
    private UrlValidator urlValidator;
    private final String url;
    private Document content;

    public PageScraper(String url) {
        this.url = url;
        urlValidator = UrlValidator.getInstance();
        scrapePage();
    }


    private void scrapePage() {
        if (urlValidator.isValid(url)) {
            try {
                this.content = Jsoup.connect(url).get();
            } catch (IOException e) {
                System.out.println("Something went wrong with scraping the page!");
            }
        } else {
            throw new IllegalArgumentException("Invalid URL!");
        }
    }

    public Document getContent() {
        return this.content;
    }
}
