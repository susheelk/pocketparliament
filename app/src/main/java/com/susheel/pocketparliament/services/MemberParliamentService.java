package com.susheel.pocketparliament.services;

import android.util.Log;

import com.susheel.pocketparliament.model.MemberParliament;

/**
 * @author Susheel Kona
 */

public class MemberParliamentService extends HttpService{
    private static final MemberParliamentService instance = new MemberParliamentService();
    public static MemberParliamentService getInstance(){
        return instance;
    }

    public MemberParliament[] getAll() throws Exception{
        String response = doRequest(HttpService.OPEN_PARL, "politicians");
        return new MemberParliament[]{}; // TODO Fill this in
    }
}
