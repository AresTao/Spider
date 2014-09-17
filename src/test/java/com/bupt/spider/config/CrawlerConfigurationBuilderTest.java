package com.bupt.spider.config;

import org.junit.Test;

final public class CrawlerConfigurationBuilderTest {

	@Test(expected = IllegalArgumentException.class)
	public void testThatWithDownloaderThrowsExceptionIfDownloaderIsNull() {
		CrawlerConfiguration.forStartPoint("http://www").withDownloader(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatWithLinkNormalizerThrowsExceptionIfNormalizerIsNull() {
		CrawlerConfiguration.forStartPoint("http://www").withLinkNormalizer(null);
	}

}
