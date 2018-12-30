package com.susheel.pocketparliament.services;

import com.susheel.pocketparliament.model.NewsItem;
import com.susheel.pocketparliament.services.parsers.NewsParser;

import java.util.List;

public class NewsService extends HttpService {
    private static final NewsService instance = new NewsService();
    public static NewsService getInstance(){
        return instance;
    }

    public List<NewsItem> get(String params) throws Exception {
        String response = doRequest(HttpService.BILLSEARCH, "news"+params);
        List<NewsItem> list = NewsParser.getInstance().listFromJson(response);
        return list;
    }

}
