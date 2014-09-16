package com.bupt.spider.page;

import com.bupt.spider.constants.Status;


/**
 * Contract for Pages factory.
 */
public interface PageFactory {
	
	Page buildOkPage(String url, String content);

	Page buildErrorPage(String url, Status status);

	Page buildRejectedMimeTypePage(String url, Status status, String mimeType);
}
