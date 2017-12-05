package com.susheel.pocketparliament.services;

/**
 * @author Susheel Kona
 */

class PartyService {
    private static final PartyService ourInstance = new PartyService();

    static PartyService getInstance() {
        return ourInstance;
    }

    private PartyService() {
    }

}
