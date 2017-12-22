package com.susheel.pocketparliament.model;

import com.susheel.pocketparliament.services.MemberParliamentService;



/**
 * @author Susheel
 */

public class MemberParliament extends Person {
    private String parlUrl;
    private String personalUrl;
    private String imageUrl;
    private String apiUrl;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    private String twitterUsername;
    private String emailAddress;
    private String phoneNumber;

    private Party party;
    private Riding riding;

    public MemberParliament(){}

    public MemberParliament(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public String getParlUrl() {
        return parlUrl;
    }

    public void setParlUrl(String parlUrl) {
        this.parlUrl = parlUrl;
    }

    public String getPersonalUrl() {
        return personalUrl;
    }

    public void setPersonalUrl(String personalUrl) {
        this.personalUrl = personalUrl;
    }

    public String getImageUrl() {
        return MemberParliamentService.OPEN_PARL+imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Riding getRiding() {
        return riding;
    }

    public void setRiding(Riding riding) {
        this.riding = riding;
    }

    public String getBlurb() {
        return getParty().getName()+" MP for "+ getRiding().getName()+", "+getRiding().getProvince();
    }


    public static MemberParliament forList(String name, String imageUrl, Riding riding, Party party){
        MemberParliament member = new MemberParliament();
        String[] names = name.split(" ");
        member.setFirstName(names[0]);
        member.setLastName(names[1]);
        member.setImageUrl(imageUrl);
        member.setRiding(riding);
        member.setParty(party);
        return member;
    }
}
