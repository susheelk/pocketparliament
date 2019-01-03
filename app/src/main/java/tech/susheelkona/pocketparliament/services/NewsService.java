package tech.susheelkona.pocketparliament.services;

import tech.susheelkona.pocketparliament.model.NewsItem;
import tech.susheelkona.pocketparliament.services.parsers.BillParser;
import tech.susheelkona.pocketparliament.services.parsers.NewsParser;

import java.util.Date;
import java.util.List;

public class NewsService extends HttpService {
    private static final NewsService instance = new NewsService();
    public static NewsService getInstance(){
        return instance;
    }

    private Date lastUpdated;

    public List<NewsItem> get(String params) throws Exception {
        String response = doRequest(BILLSEARCH, "news"+params);
        List<NewsItem> list = NewsParser.getInstance().listFromJson(response);
        lastUpdated = BillParser.getLastUpdated(response);
        return list;
    }

}
