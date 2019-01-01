package tech.susheelkona.pocketparliament.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tech.susheelkona.pocketparliament.model.Party;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Susheel Kona
 */

public class PartyService {

    private static final PartyService ourInstance = new PartyService();
    public static PartyService getInstance() {
        return ourInstance;
    }

    private List<Party> partyList;

    private ObjectMapper mapper;
    private InputStream inputStream;

    private PartyService() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        partyList = new ArrayList<>();
        partyList.add(new Party("c"));
        partyList.add(new Party("d"));
        partyList.add(new Party("e"));
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void write(){
        try {
            String test = mapper.writeValueAsString(partyList);
            int i = 0;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void loadParties() {
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            if (inputStream != null){
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String json = buffer.toString();
                partyList = mapper.readValue(json, new TypeReference<List<Party>>(){});
                int i =0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Party getPartyByName(String name) {
        for (Party party: partyList) {
            if(party.getName().matches(name)) return party;
        }
        return null;
    }

}
