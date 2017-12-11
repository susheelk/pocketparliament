package com.susheel.pocketparliament.services;



import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.services.filters.Filter;
import com.susheel.pocketparliament.services.parsers.MemberParliamentParser;


import java.util.List;


/**
 * @author Susheel Kona
 */

public class MemberParliamentService extends HttpService{
    private static final MemberParliamentService instance = new MemberParliamentService();
    public static MemberParliamentService getInstance(){
        return instance;
    }

    public List<MemberParliament> get(Filter<MemberParliament> filter) throws Exception{
        String response = doRequest(HttpService.OPEN_PARL, "politicians");
        List<MemberParliament> data =  MemberParliamentParser.getInstance().fromJson(response);

        return filter.doFilter(data);
    }
}
