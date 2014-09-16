/**
 * 
 */
package com.bupt.spider.page;

import java.util.ArrayList;
import java.util.List;

import com.bupt.spider.constants.Status;
import com.bupt.spider.link.DefaultLinkFinder;
import com.bupt.spider.link.FrameLinkFinder;
import com.bupt.spider.link.IframeLinkFinder;

public class OkPage implements Page {

	private final String url;
	private final String content;

	public OkPage(final String url, final String content) {
		if ((url == null) || (url.trim().length() == 0)) {
			throw new IllegalArgumentException("url cannot be null");
		}
		this.url = url;
		this.content = content;
	}

	public List<String> getLinks() {

		List<String> list = new ArrayList<String>();
		list.addAll(new DefaultLinkFinder(content).getLinks());
		list.addAll(new IframeLinkFinder(content).getLinks());
		list.addAll(new FrameLinkFinder(content).getLinks());

		return list;
	}

	public String getUrl() {
		return url;
	}

	public String getContent() {
		return content;
	}

	public Status getStatusCode() {
		return Status.OK;
	}

	public String getCharset() {
		return "UTF-8";
	}
}
