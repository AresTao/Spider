package com.bupt.spider.visitor;

import com.bupt.spider.constants.Status;
import com.bupt.spider.model.Url;
import com.bupt.spider.page.Page;

public interface ContentVisitor {
	
	void visit(Page page);
	
	void onError(Url errorUrl, Status statusError);
}
