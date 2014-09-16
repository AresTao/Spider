/**
 * 
 */
package com.bupt.spider.visitor;

import com.bupt.spider.model.Url;

public interface PageVisitor extends ContentVisitor {

    boolean followUrl(Url url);

}
