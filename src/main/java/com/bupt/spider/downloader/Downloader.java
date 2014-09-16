/**
 * 
 */
package com.bupt.spider.downloader;

import com.bupt.spider.page.Page;

public interface Downloader {

    Page get(String url);

}
