package com.susheel.pocketparliament.services.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.susheel.pocketparliament.model.NewsItem;
import com.susheel.pocketparliament.model.legislation.Bill;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class NewsParser {

    private static NewsParser instance = new NewsParser();
    public static NewsParser getInstance() {
        return instance;
    }

    private final ObjectMapper mapper = new ObjectMapper(){{
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }};

    public List<NewsItem> listFromJson(String json) throws IOException {
        JsonNode root = mapper.readTree(json);
        JsonNode data = root.get("data");
        String arrString = data.toString();
        return Arrays.asList(mapper.readValue(arrString, NewsItem[].class));
    }
}
