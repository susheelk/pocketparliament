package com.susheel.pocketparliament.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.susheel.pocketparliament.services.MemberParliamentService;



/**
 * @author Susheel
 */

public class MemberParliament extends Person implements Parcelable, Searchable {
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
        if (party != null){
            this.party = party;
        } else {
            this.party = Party.fromName("Unknown");
        }
    }

    public Riding getRiding() {
        return riding;
    }

    public void setRiding(Riding riding) {
        this.riding = riding;
    }

    public String getBlurb() {
        try {
            return getParty().getName()+" MP for "+ getRiding().getName()+", "+getRiding().getProvince();
        } catch (Exception e) {
            System.out.println(getName());
        }
        return null;
    }

    public void setName(String name) {
        String[] names = name.split(" ");
        setFirstName(names[0]);
        setLastName(names[1]);
    }


    public static MemberParliament forList(String name, String imageUrl, Riding riding, Party party, String apiUrl){
        MemberParliament member = new MemberParliament();
        member.setName(name);
        member.setImageUrl(imageUrl);
        member.setRiding(riding);
        member.setParty(party);
        member.setApiUrl(apiUrl);
        return member;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.parlUrl);
        dest.writeString(this.personalUrl);
        dest.writeString(this.imageUrl);
        dest.writeString(this.apiUrl);
        dest.writeString(this.twitterUsername);
        dest.writeString(this.emailAddress);
        dest.writeString(this.phoneNumber);
        dest.writeParcelable(this.party, flags);
        dest.writeParcelable(this.riding, flags);
    }

    protected MemberParliament(Parcel in) {
        this.parlUrl = in.readString();
        this.personalUrl = in.readString();
        this.imageUrl = in.readString();
        this.apiUrl = in.readString();
        this.twitterUsername = in.readString();
        this.emailAddress = in.readString();
        this.phoneNumber = in.readString();
        this.party = in.readParcelable(Party.class.getClassLoader());
        this.riding = in.readParcelable(Riding.class.getClassLoader());
    }

    public static final Parcelable.Creator<MemberParliament> CREATOR = new Parcelable.Creator<MemberParliament>() {
        @Override
        public MemberParliament createFromParcel(Parcel source) {
            return new MemberParliament(source);
        }

        @Override
        public MemberParliament[] newArray(int size) {
            return new MemberParliament[size];
        }
    };

    @Override
    public boolean contains(String query) {
        return (getName().contains(query));
    }
}
