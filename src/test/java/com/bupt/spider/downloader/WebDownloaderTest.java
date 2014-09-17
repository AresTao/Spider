package com.bupt.spider.downloader;

import org.junit.Assert;
import org.junit.Test;

import com.bupt.spider.page.Page;

final public class WebDownloaderTest {

	@Test
	public void testThatEncodesURLsBeforeCallingGetMethod() {
		Page page = new WebDownloader().get("http://www.google.com/search?q=program");
		Assert.assertNotNull(page);
		Assert.assertTrue(page.getContent().contains("program"));
		Assert.assertEquals("http://www.google.com/search?q=program", page.getUrl());
	}

	@Test
	public void testThatDownloadsURLs() {
		Page page = new WebDownloader().get("http://www.google.com");
		Assert.assertNotNull(page);
		Assert.assertTrue(page.getContent().contains("google"));
	}

}
