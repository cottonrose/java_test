package com.bittech.tangshianalyze.crawler.prase;

import com.bittech.tangshianalyze.crawler.common.Page;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Component;

/**
 * Author: secondriver
 * Created: 2018/12/11
 */
@Component
public class DataParse implements Parse {
    
    private String dynastyXpath = "//div[@class='cont']/p[@class='source'][1]/a[1]/text()";
    private String authorXpath = "//div[@class='cont']/p[@class='source'][1]/a[2]/text()";
    private String titleXpath = "//div[@class='cont']/h1/text()";
    private String contentXpath = "//div[@class='cont']/div[@class='contson']";
    
    @Override
    public void parse(Page page) {
        if (!page.isDetail()) {
            return;
        }
        try {
            HtmlPage htmlPage = page.getHtmlPage();
            HtmlElement body = htmlPage.getBody();
            String dynasty = ((DomText) body.getByXPath(dynastyXpath).get(0)).asText();
            String author = ((DomText) body.getByXPath(authorXpath).get(0)).asText();
            String title = ((DomText) body.getByXPath(titleXpath).get(0)).asText();
            HtmlDivision division = (HtmlDivision) body.getByXPath(contentXpath).get(0);
            String content = division.getTextContent();
            page.getDataSet().putData("dynasty", dynasty);
            page.getDataSet().putData("author", author);
            page.getDataSet().putData("title", title);
            page.getDataSet().putData("content", content);
            page.getDataSet().putData("url", page.getUrl());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
