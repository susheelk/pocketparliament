package com.susheel.pocketparliament.services.parsers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.susheel.pocketparliament.model.legislation.Bill;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillParser {
    private static BillParser instance = new BillParser();
    public static BillParser getInstance() {
        return instance;
    }

    private final ObjectMapper mapper = new ObjectMapper(){{
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }};

    public List<Bill> listFromJson(String json) throws IOException {
//        Map<String, String> root = mapper.readValue(json, new TypeReference<HashMap<String, String>>(){});
        JsonNode root = mapper.readTree(json);
        JsonNode data = root.get("data");
        String arrString = data.toString();
        return Arrays.asList(mapper.readValue(arrString, Bill[].class));
    }

    public Bill objectFromJson(String json) throws IOException {
        return mapper.readValue(json, Bill.class);
    }
}
