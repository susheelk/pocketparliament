package com.susheel.pocketparliament.services.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.model.Party;
import com.susheel.pocketparliament.model.Riding;
import com.susheel.pocketparliament.services.PartyService;

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
    final PartyService partyService = PartyService.getInstance();

    public List<MemberParliament> listFromJson(String json) throws IOException {
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
            String apiUrl = memberNode.get("url").asText();

            list.add(MemberParliament.forList(name, imageUrl, riding, partyService.getPartyByName(partyName), apiUrl));
            System.out.println();
        }

        Collections.sort(list, (a, b) -> a.getLastName().compareTo(b.getLastName()));
        return list;
    }

    public MemberParliament objectFromJson(String json) throws IOException {
        MemberParliament object = new MemberParliament();
        JsonNode root = mapper.readTree(json);

        JsonNode linksNode = root.get("links");
        Iterator<JsonNode> iterator = linksNode.elements();
        while (iterator.hasNext()) {
            JsonNode link = iterator.next();
            if(link.get("note").asText().equals("Page on parl.gc.ca")) {
                object.setParlUrl(link.get("url").asText());
            }
            if (link.get("note").asText().equals("Official site")) {
                object.setPersonalUrl(link.get("url").asText());
            }
        }

        Riding riding = new Riding();
        Party party = new Party();
        JsonNode membershipsNode = root.get("memberships");
        iterator = membershipsNode.elements();
        while (iterator.hasNext()) {
            JsonNode membership = iterator.next();
            try {
                JsonNode ridingNode = membership.path("riding");
                String province = ridingNode.get("province").asText();
                JsonNode nameNode = ridingNode.path("name");
                String name = nameNode.get("en").asText();
                riding = Riding.forList(name, province);

                JsonNode partyNode = membership.path("party");
                JsonNode partyNameNode = partyNode.path("short_name").get("en");
                party = partyService.getPartyByName(partyNameNode.asText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        JsonNode otherInfoNode = root.path("other_info");
        JsonNode twitterNameNode = otherInfoNode.get("twitter");
        iterator = twitterNameNode.elements();
        String twitterName = "";
        while (iterator.hasNext()) {
            twitterName = iterator.next().asText();
        }

        JsonNode idsNode = otherInfoNode.get("parl_id");
        iterator = idsNode.elements();
        int id = iterator.next().asInt();
        object.setId(id);

        object.setRiding(riding);
        object.setParty(party);
        object.setImageUrl(root.get("image").asText());
        object.setFirstName(root.get("given_name").asText());
        object.setLastName(root.get("family_name").asText());
        object.setEmailAddress(root.get("email").asText());
        object.setPhoneNumber(root.get("voice").asText());
        object.setImageUrl(root.get("image").asText());
        object.setTwitterUsername(twitterName);


        return object;
    }
}
