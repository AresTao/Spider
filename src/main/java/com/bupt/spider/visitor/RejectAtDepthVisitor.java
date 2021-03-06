package com.bupt.spider.visitor;

import com.bupt.spider.constants.Status;
import com.bupt.spider.model.Url;
import com.bupt.spider.page.Page;

/**
 * @author jonasabreu
 * 
 */
final public class RejectAtDepthVisitor implements PageVisitor {

    private final PageVisitor visitor;
    private final int depth;

    public RejectAtDepthVisitor(final int depth, final PageVisitor visitor) {
        this.depth = depth;
        this.visitor = visitor;
    }

    public boolean followUrl(final Url url) {
        if (url.depth() > depth) {
            return false;
        }
        return visitor.followUrl(url);
    }

    public void onError(final Url errorUrl, final Status statusError) {
        visitor.onError(errorUrl, statusError);
    }

    public void visit(final Page page) {
        visitor.visit(page);
    }

}
