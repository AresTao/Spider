/**
 * 
 */
package com.bupt.spider.service;


import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bupt.spider.downloader.Downloader;
import com.bupt.spider.normalizer.DefaultLinkNormalizer;
import com.bupt.spider.normalizer.LinkNormalizer;
import com.bupt.spider.page.OkPage;
import com.bupt.spider.visitor.PageVisitor;

public class PageCrawlerTest {

	private Mockery mockery;
	Downloader downloader;
	PageVisitor visitor;
	private LinkNormalizer normalizer;

	@Before
	public void setUp() {
		mockery = new Mockery();
		downloader = mockery.mock(Downloader.class);
		visitor = mockery.mock(PageVisitor.class);
		normalizer = mockery.mock(LinkNormalizer.class);
	}

	@After
	public void tearDown() {
		mockery.assertIsSatisfied();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatThrowsExceptionIfUrlIsNull() {
		new PageCrawler(null, downloader, normalizer);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatThrowsExceptionIfUrlIsBlank() {
		new PageCrawler("   \n   \t   ", downloader, normalizer);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatThrowsExceptionIfUrlDoesntBeginWithHttp() {
		new PageCrawler("url", downloader, normalizer);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatThrowsExceptionIfVisitorIsNull() {
		new PageCrawler("http://asdasdasdasdasd", downloader, normalizer).crawl(null);
	}

	@Test
	public void testThatCrawlerAvoidCircles() {
		mockery.checking(new Expectations() {
			{
				one(downloader).get("http://test.com");
				will(returnValue(new OkPage("http://test.com",
						"<a href=\"http://test.com\"><a href=\"http://test.com\">")));

				one(visitor).visit(with(any(OkPage.class)));

			}
		});
		new PageCrawler("http://test.com", downloader, new DefaultLinkNormalizer("http://test.com")).crawl(visitor);
	}

}
