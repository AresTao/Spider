package com.bupt.spider.page;

import java.util.List;

import com.bupt.spider.constants.Status;

/**
 * @author jonasabreu
 * 
 */
public interface Page {

    public List<String> getLinks();

    public String getUrl();

    public String getContent();

    public Status getStatusCode();

    public String getCharset();

}
