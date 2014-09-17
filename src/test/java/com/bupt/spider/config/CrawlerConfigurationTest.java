package com.bupt.spider.config;

import org.junit.Test;

final public class CrawlerConfigurationTest {

	@Test(expected = IllegalArgumentException.class)
	public void testThatThrowsExceptionIfBeginUrlIsNull() {
		new CrawlerConfiguration(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatThrowsExceptionIfBeginUrlIsEmpty() {
		new CrawlerConfiguration("  \t   \n  ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatThrowsExceptionIfBeginUrlDoesNotBeginWithHttp() {
		new CrawlerConfiguration("url");
	}

	@Test
	public void testThatDoesNotThrowExceptionIfBeginUrlBeginWithHttp() {
		new CrawlerConfiguration("http://");
	}

	@Test
	public void testThatDoesNotThrowExceptionIfBeginUrlBeginWithHttps() {
		new CrawlerConfiguration("https://");
	}

}
