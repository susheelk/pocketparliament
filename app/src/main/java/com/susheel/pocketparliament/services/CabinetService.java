package com.susheel.pocketparliament.services;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.susheel.pocketparliament.model.CabinetMember;
import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.services.parsers.MemberParliamentParser;

import java.util.ArrayList;
import java.util.List;

public class CabinetService extends HttpService{
    private static final CabinetService ourInstance = new CabinetService();
    public static CabinetService getInstance() {
        return ourInstance;
    }

    private MemberParliamentParser parser = new MemberParliamentParser();

    private static List<CabinetMember> cache = new ArrayList<>();

    public List<CabinetMember> getAll() throws Exception {
        if(cache == null || cache.isEmpty()){
            String response = doRequest(HttpService.BILLSEARCH, "cabinet?size=50");
            cache = parser.cabinetMembersListFromJson(response);
        }
        return cache;
    }

    public CabinetMember getByName(String name) throws Exception {
        List<CabinetMember> list = Stream.of(getAll()).filter(cabinetMember -> cabinetMember.getName().matches(name)).collect(Collectors.toList());
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

}
