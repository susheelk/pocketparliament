package tech.susheelkona.pocketparliament.android.tasks;

import android.content.Context;
import android.content.SharedPreferences;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import tech.susheelkona.pocketparliament.model.MemberParliament;
import tech.susheelkona.pocketparliament.model.legislation.Bill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SharedPreferenceHelper {

    public static final String FOLLOWED_ONLY = "followed";

    private static final SharedPreferenceHelper ourInstance = new SharedPreferenceHelper();
    public static SharedPreferenceHelper getInstance() {
        return ourInstance;
    }

    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences("tech.susheelkona.pocketparliament.follow_prefs", Context.MODE_PRIVATE);
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
//        return Arrays.asList(getSharedPreferences(context).getString("mp_follows", "").split(","));
        return getStringList("mp_follows", context);
    }

    public boolean isFollowed(MemberParliament memberParliament, Context context) {
        return getFollowedMemberParliaments(context).contains(memberParliament.getName());
    }

    public void toggleFollowBill(Bill bill, Context context) {
        List<String> followed = new ArrayList<>(getFollowedBills(context));
        if(followed.contains(bill.getNumber())) {
            followed.remove(bill.getNumber());
        } else {
            followed.add(bill.getNumber());
        }
        storeStringList("bill_follows", followed, context);
    }

    public List<String> getFollowedBills(Context context){
        List<String> follows = getStringList("bill_follows", context);
        follows = Stream.of(follows).filter(s -> s.startsWith("S-") || s.startsWith("C-")).collect(Collectors.toList());
        return follows;
    }

    public boolean isFollowed(Bill bill, Context context){
        return getFollowedBills(context).contains(bill.getNumber());
    }

    private List<String> getStringList(String key, Context context){
        return Arrays.asList(getSharedPreferences(context).getString(key, "").split(","));
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


    public void storeLastCheck(int lastVote, String lastNews, Context context){
        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("last_news_tag", lastNews);
        editor.putInt("last_vote_id", lastVote);
        editor.apply();
    }

    public int getLastVote(Context context){
        return getSharedPreferences(context).getInt("last_vote_id", -1);
    }

    public String getLastNews(Context context) {
        return getSharedPreferences(context).getString("last_news_tag", "EMPTY");
    }

}
