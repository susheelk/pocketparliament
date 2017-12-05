package com.susheel.pocketparliament.services.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.model.Party;
import com.susheel.pocketparliament.model.Riding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Susheel Kona
 */

public class MemberParliamentParser {
    private static final MemberParliamentParser ourInstance = new MemberParliamentParser();

    public static MemberParliamentParser getInstance() {
        return ourInstance;
    }

    final ObjectMapper mapper = new ObjectMapper();


    public List<MemberParliament> fromJson(String json) throws IOException {
        List<MemberParliament> list = new ArrayList<>();
        JsonNode root = mapper.readTree(json);
        JsonNode objectsNode = root.get("objects");

        Iterator<JsonNode> iterator = objectsNode.elements();
        while (iterator.hasNext()) {
            JsonNode memberNode = iterator.next();

            JsonNode ridingInfo = memberNode.path("current_riding");
            Riding riding = Riding.forList(ridingInfo.path("name").get("en").asText(), ridingInfo.get("province").asText());

            String name = memberNode.get("name").asText();
            String imageUrl = memberNode.get("image").asText();
            String partyName = memberNode.path("current_party").path("short_name").get("en").asText();

            list.add(MemberParliament.forList(name, imageUrl, riding, Party.fromName(partyName)));
        }


        return list;
    }
}
