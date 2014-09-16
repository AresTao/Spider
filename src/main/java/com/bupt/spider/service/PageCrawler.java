/**
 * 
 */
package com.bupt.spider.service;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.bupt.spider.config.CrawlerConfiguration;
import com.bupt.spider.downloader.Downloader;
import com.bupt.spider.exception.CrawlerException;
import com.bupt.spider.model.ExecutorCounter;
import com.bupt.spider.model.Url;
import com.bupt.spider.normalizer.LinkNormalizer;
import com.bupt.spider.task.PageCrawlerExecutor;
import com.bupt.spider.visitor.DoesNotFollowVisitedUrlVisitor;
import com.bupt.spider.visitor.PageVisitor;

public class PageCrawler {

	private final Logger log = Logger.getLogger(PageCrawler.class);

	private final CrawlerConfiguration config;

	public PageCrawler(final CrawlerConfiguration config) {
		this.config = config;
	}

	public PageCrawler(final String beginUrl) {
		this(CrawlerConfiguration.forStartPoint(beginUrl).build());
	}

	public PageCrawler(final String beginUrl, final Downloader downloader, final LinkNormalizer normalizer) {
		config = CrawlerConfiguration.forStartPoint(beginUrl).withDownloader(downloader).withLinkNormalizer(normalizer)
				.build();
	}

	public void crawl(final PageVisitor visitor) {
		if (visitor == null) {
			throw new IllegalArgumentException("visitor cannot be null");
		}

		ThreadPoolExecutor executor = new ThreadPoolExecutor(config.minPoolSize(), config.maxPoolSize(), config
				.keepAliveMilliseconds(), TimeUnit.MILLISECONDS, new DelayedBlockingQueue(config
				.requestDelayMilliseconds()));

		final ExecutorCounter counter = new ExecutorCounter();

		try {
			executor
					.execute(new PageCrawlerExecutor(new Url(config.beginUrl(), 0), executor, counter, config
							.downloader(), config.normalizer(), new DoesNotFollowVisitedUrlVisitor(config.beginUrl(),
							visitor)));

			while (counter.value() != 0) {
				log.debug("executors that finished: " + executor.getCompletedTaskCount());
				log.debug("Number of Executors alive: " + counter.value());
				sleep();
			}
		} finally {
			executor.shutdown();
		}
	}

	private void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new CrawlerException("main thread died. ", e);
		}

	}
}
