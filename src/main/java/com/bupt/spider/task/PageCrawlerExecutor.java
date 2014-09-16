package com.bupt.spider.task;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

import com.bupt.spider.constants.Status;
import com.bupt.spider.downloader.Downloader;
import com.bupt.spider.model.ExecutorCounter;
import com.bupt.spider.model.Url;
import com.bupt.spider.normalizer.LinkNormalizer;
import com.bupt.spider.page.Page;
import com.bupt.spider.visitor.PageVisitor;

final public class PageCrawlerExecutor implements Runnable {

    private final Downloader downloader;
    private final LinkNormalizer normalizer;
    private final PageVisitor visitor;
    private final ExecutorCounter counter;

    private final Logger log = Logger.getLogger(PageCrawlerExecutor.class);
    private final Url urlToCrawl;
    private final ThreadPoolExecutor executor;

    public PageCrawlerExecutor(final Url urlToCrawl, final ThreadPoolExecutor executor, final ExecutorCounter counter,
            final Downloader downloader, final LinkNormalizer normalizer, final PageVisitor visitor) {
        this.urlToCrawl = urlToCrawl;
        this.executor = executor;
        this.counter = counter;
        this.downloader = downloader;
        this.normalizer = normalizer;
        this.visitor = visitor;

        counter.increase();
    }

    public void run() {
        try {

            log.info("crawling url: " + urlToCrawl.link());

            Page page = downloader.get(urlToCrawl.link());
            if (page.getStatusCode() != Status.OK) {
                visitor.onError(urlToCrawl, page.getStatusCode());
            } else {
                visitor.visit(page);
            }

            for (String l : page.getLinks()) {
                String link = normalizer.normalize(l);
                final Url url = new Url(link, urlToCrawl.depth() + 1);
                if (visitor.followUrl(url)) {
                    executor.execute(new PageCrawlerExecutor(url, executor, counter, downloader, normalizer, visitor));
                }
            }

        } finally {
            counter.decrease();
        }
    }

}
