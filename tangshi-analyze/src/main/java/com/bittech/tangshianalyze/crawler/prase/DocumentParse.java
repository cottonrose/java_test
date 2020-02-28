package com.bittech.tangshianalyze.crawler.prase;

import com.bittech.tangshianalyze.crawler.common.Page;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Component;

/**
 * Author: secondriver
 * Created: 2018/12/11
 */
@Component
public class DocumentParse implements Parse {
    
    @Override
    public void parse(Page page) {
        if (page.isDetail()) {
            return;
        }
        HtmlPage htmlPage = page.getHtmlPage();
        htmlPage
                .getBody()
                .getElementsByAttribute("div", "class", "typecont")
                .forEach(htmlElement -> {
                    htmlElement.getElementsByTagName("a")
                            .forEach(e -> {
                                String path = e.getAttribute("href");
                                Page subPage = new Page(path, page.getBase());
                                subPage.setDetail(true);
                                page.put(subPage);
                            });
                });
    }
}