package tech.susheelkona.pocketparliament.services.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tech.susheelkona.pocketparliament.model.legislation.Bill;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

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
