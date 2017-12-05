package com.susheel.pocketparliament.services;

import android.util.Log;

import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.services.parsers.MemberParliamentParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Susheel Kona
 */

public class MemberParliamentService extends HttpService{
    private static final MemberParliamentService instance = new MemberParliamentService();
    public static MemberParliamentService getInstance(){
        return instance;
    }

    public List<MemberParliament> getAll() throws Exception{
        String response = doRequest(HttpService.OPEN_PARL, "politicians");
        return MemberParliamentParser.getInstance().fromJson(response);
    }
}
