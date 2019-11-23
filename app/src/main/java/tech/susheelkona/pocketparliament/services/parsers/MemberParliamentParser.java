package tech.susheelkona.pocketparliament.services.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import tech.susheelkona.pocketparliament.model.CabinetMember;
import tech.susheelkona.pocketparliament.model.MemberParliament;
import tech.susheelkona.pocketparliament.model.Party;
import tech.susheelkona.pocketparliament.model.Riding;
import tech.susheelkona.pocketparliament.services.CabinetService;
import tech.susheelkona.pocketparliament.services.PartyService;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Arrays;
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
    final CabinetService cabinetService = CabinetService.getInstance();

    public List<MemberParliament> listFromJson(String json) throws Exception {
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

            MemberParliament memberParliament = MemberParliament.forList(name, imageUrl, riding, partyService.getPartyByName(partyName), apiUrl);

            CabinetMember billSearchMember = cabinetService.getByName(name);

            if (billSearchMember != null) {
                CabinetMember member = new CabinetMember();
                member.setOrderOfPrecedence(billSearchMember.getOrderOfPrecedence());
                member.setPosition(billSearchMember.getPosition());

                member.setImageUrl(imageUrl);
                member.setRiding(riding);
                member.setParty(partyService.getPartyByName(partyName));
                member.setApiUrl(apiUrl);
                member.setName(name);
                list.add(member);
                continue;
            }

            list.add(memberParliament);
            System.out.println();
        }

        boolean allCabinets = true;
        for(MemberParliament mp: list){
            if (!(mp instanceof CabinetMember)){
                allCabinets = false;
                break;
            }
        }

        Collections.sort(list, (a, b) -> a.getLastName().compareTo(b.getLastName()));

        return list;
    }

    public MemberParliament objectFromJson(String json) throws Exception {
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

        if(twitterNameNode != null) {
            iterator = twitterNameNode.elements();
            String twitterName = "";
            while (iterator.hasNext()) {
                twitterName = iterator.next().asText();
                object.setTwitterUsername(twitterName);
            }
        }

        JsonNode idsNode = otherInfoNode.get("parl_id");
        if (idsNode != null) {
            iterator = idsNode.elements();
            int id = iterator.next().asInt();
            object.setId(id);
        }

        object.setRiding(riding);
        object.setParty(party);
        object.setImageUrl(root.get("image").asText());
        object.setFirstName(root.get("given_name").asText());
        object.setLastName(root.get("family_name").asText());
        object.setEmailAddress(root.get("email") != null ? root.get("email").asText() : null);
        object.setPhoneNumber(root.get("voice") != null ? root.get("voice").asText() : null);
        object.setImageUrl(root.get("image").asText());

        CabinetMember billSearchMember = cabinetService.getByName(object.getName());

        if (billSearchMember != null) {
            CabinetMember member = new CabinetMember();
            member.setOrderOfPrecedence(billSearchMember.getOrderOfPrecedence());
            member.setPosition(billSearchMember.getPosition());
            member.setRiding(riding);
            member.setParty(party);
            member.setImageUrl(root.get("image").asText());
            member.setFirstName(root.get("given_name").asText());
            member.setLastName(root.get("family_name").asText());
            member.setEmailAddress(root.get("email").asText());
            member.setPhoneNumber(root.get("voice").asText());
            member.setImageUrl(root.get("image").asText());
            return member;
        }


        return object;
    }


    public List<CabinetMember> cabinetMembersListFromJson(String json) throws IOException {
        JsonNode root = mapper.readTree(json);
        JsonNode data = root.get("data");
        String arrString = data.toString();
        return Arrays.asList(mapper.readValue(arrString, CabinetMember[].class));
    }
}
