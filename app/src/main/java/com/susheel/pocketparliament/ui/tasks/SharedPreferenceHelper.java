package com.susheel.pocketparliament.ui.tasks;

import android.content.Context;
import android.content.SharedPreferences;

import com.susheel.pocketparliament.model.MemberParliament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreferenceHelper {
    private static final SharedPreferenceHelper ourInstance = new SharedPreferenceHelper();
    public static SharedPreferenceHelper getInstance() {
        return ourInstance;
    }

    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences("com.susheel.pocketparliament.follow_prefs", Context.MODE_PRIVATE);
    }

    public void toggleFollowMemberParliament(MemberParliament person, Context context) {
        List<String> followed = new ArrayList<>(getFollowedMemberParliaments(context));
        if(followed.contains(person.getName())) {
            followed.remove(person.getName());
        } else {
            followed.add(person.getName());
        }
        storeStringList("mp_follows", followed, context);
    }

    public List<String> getFollowedMemberParliaments(Context context){
        return Arrays.asList(getSharedPreferences(context).getString("mp_follows", "").split(","));
    }

    private void storeStringList(String key, List<String> list, Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        StringBuffer buffer = new StringBuffer();
        for(String s: list){
            buffer.append(s+",");
        }
        String str = buffer.toString();
        editor.putString(key, str.substring(0, str.length() - 1));
        editor.apply();
    }

    public boolean isFollowed(MemberParliament memberParliament, Context context) {
        return getFollowedMemberParliaments(context).contains(memberParliament.getName());
    }

}
