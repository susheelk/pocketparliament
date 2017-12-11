package com.susheel.pocketparliament.services.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.model.Party;
import com.susheel.pocketparliament.model.Riding;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Collections;

import java.util.Iterator;

import java.util.List;
;

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
            JsonNode ridingName = ridingInfo.path("name");
            String rname = ridingName.get("en").asText();
            Riding riding = Riding.forList(ridingName.get("en").asText(), ridingInfo.get("province").asText());

            String name = memberNode.get("name").asText();
            String imageUrl = memberNode.get("image").asText();
            String partyName = memberNode.path("current_party").path("short_name").get("en").asText();

            list.add(MemberParliament.forList(name, imageUrl, riding, Party.fromName(partyName)));
            System.out.println();
        }

        Collections.sort(list, (a, b) -> a.getLastName().compareTo(b.getLastName()));
        return list;
    }
}
