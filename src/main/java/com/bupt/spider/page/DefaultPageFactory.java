package com.bupt.spider.page;

import com.bupt.spider.constants.Status;

/**
 * Default implementation for {@link PageFactory}.
 */
public class DefaultPageFactory implements PageFactory {
	
	public Page buildOkPage(String url, String content) {
		return new OkPage(url, content);
	}

	public Page buildErrorPage(String url, Status status) {
		return new ErrorPage(url, status);
	}

	public Page buildRejectedMimeTypePage(String url, Status status, String mimeType) {
		return new RejectedMimeTypePage(url, status, mimeType);
	}
}
