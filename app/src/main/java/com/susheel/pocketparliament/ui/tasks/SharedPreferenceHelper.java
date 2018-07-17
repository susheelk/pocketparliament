package com.susheel.pocketparliament.ui.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

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
        List<String> followed = new ArrayList<>(getFollowedMemberParliamentUrls(context));
        if(followed.contains(person.getParlUrl())) {
            followed.remove(person.getParlUrl());
        } else {
            followed.add(person.getParlUrl());
        }
        storeStringList("followed_mps", followed, context);
    }

    public List<String> getFollowedMemberParliamentUrls(Context context){
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

}
