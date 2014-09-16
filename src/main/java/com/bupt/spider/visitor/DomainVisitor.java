/**
 * 
 */
package com.bupt.spider.visitor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bupt.spider.constants.Status;
import com.bupt.spider.model.Url;
import com.bupt.spider.page.Page;

/**
 * Simple visitor to crawl a single domain.
 *  
 */
public class DomainVisitor implements PageVisitor {

    private final String domain;
    private final ContentVisitor visitor;

    public DomainVisitor(final String baseUrl, final ContentVisitor visitor) {
        this.visitor = visitor;
        if ((baseUrl == null) || (baseUrl.trim().length() == 0)) {
            throw new IllegalArgumentException("baseUrl cannot be null or empty");
        }
        Matcher matcher = Pattern.compile("(http://[^/]+)").matcher(baseUrl);
        if (!matcher.find()) {
            throw new IllegalArgumentException("baseUrl must match http://[^/]+");
        }
        domain = matcher.group(1) + "/";
    }

    public boolean followUrl(final Url url) {
        if (url == null) {
            return false;
        }
        return url.link().startsWith(domain);
    }

    // Delegate methods

    public void onError(final Url url, final Status statusError) {
        visitor.onError(url, statusError);
    }

    public void visit(final Page page) {
        visitor.visit(page);
    }

}
