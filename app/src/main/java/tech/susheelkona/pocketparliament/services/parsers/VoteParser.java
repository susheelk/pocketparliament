package tech.susheelkona.pocketparliament.services.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tech.susheelkona.pocketparliament.model.legislation.Vote;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class VoteParser {

    private final ObjectMapper mapper = new ObjectMapper(){{
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }};

    private static final VoteParser instance = new VoteParser();
    public static VoteParser getInstance(){
        return instance;
    }

    public List<Vote> listFromJson(String json) throws IOException {
        JsonNode root = mapper.readTree(json);
        JsonNode data = root.get("data");
        String arrString = data.toString();
        return Arrays.asList(mapper.readValue(arrString, Vote[].class));
    }

    public Vote objectFromJson(String json) throws IOException {
        Vote vote =  mapper.readValue(json, Vote.class);
        return vote;
    }
}
