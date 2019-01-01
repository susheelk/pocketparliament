package tech.susheelkona.pocketparliament.services.filters;

import android.content.Context;
import android.os.Bundle;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import tech.susheelkona.pocketparliament.model.CabinetMember;
import tech.susheelkona.pocketparliament.model.MemberParliament;
import tech.susheelkona.pocketparliament.android.tasks.SharedPreferenceHelper;
import tech.susheelkona.pocketparliament.model.legislation.Ballot;
import tech.susheelkona.pocketparliament.model.legislation.Vote;

import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * @author Susheel Kona
 */
// TODO implement mp filter
public class MemberParliamentFilter extends Filter<MemberParliament> {

    private Context context;

    public MemberParliamentFilter(Map<String, Object> filters) {
        super(filters);
    }

    public MemberParliamentFilter(Map<String, Object> filters, Context context) {
        super(filters);
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MemberParliamentFilter(){}

    @Override
    public List<MemberParliament> doFilter(List<MemberParliament> data) {
//        System.out.println("Initiated Filters");
        if (filters != null) {
            for(Map.Entry<String, Object> entry: filters.entrySet()) {
                switch (entry.getKey()) {
                    case FilterParameters.GROUP: data = group(data, entry.getValue()); break;
                    case FilterParameters.QUERY: data = forQuery(data, entry.getValue()); break;
                    case FilterParameters.NAME: data = name(data, entry.getValue()); break;
                    case FilterParameters.FOLLOWED: data = followed(data, true); break;
                    case FilterParameters.VOTED_FOR: data = forVoteResult(data, (Vote)(entry.getValue()), "Yea"); break;
                    case FilterParameters.VOTED_AGAINST: data = forVoteResult(data, (Vote)(entry.getValue()), "Nay"); break;
                }
                continue;
            }
        }
        return data;
    }

    private List<MemberParliament> group(List<MemberParliament> data, Object value) {
        switch (value.toString()) {
            case FilterParameters.GOVERNMENT:
                return Stream.of(data).filter(member-> member.getParty().isGovernment()).collect(Collectors.toList());
            case FilterParameters.OPPOSITION:
                return Stream.of(data).filter(member-> !member.getParty().isGovernment()).collect(Collectors.toList());
            case FilterParameters.CABINET:
                data =  Stream.of(data).filter(member -> member instanceof CabinetMember).collect(Collectors.toList());
                Collections.sort(data, (a, b) -> {
                    Integer oA = ((CabinetMember)a).getOrderOfPrecedence();
                    Integer oB = ((CabinetMember)b).getOrderOfPrecedence();
                    return oA.compareTo(oB);
                });
                return data;


        }
        return data;
    }

    private List<MemberParliament> forQuery(List<MemberParliament> data, Object value) {
        return Stream.of(data).filter(member -> member.contains(value.toString())).collect(Collectors.toList());
    }

    private List<MemberParliament> name(List<MemberParliament> data, Object value){
        return Stream.of(data).filter(member -> member.getName().matches(value.toString())).collect(Collectors.toList());
    }

    private List<MemberParliament> followed(List<MemberParliament> data, boolean followed) {
        SharedPreferenceHelper helper = SharedPreferenceHelper.getInstance();
        return Stream.of(data).filter(member -> helper.isFollowed(member, context)).collect(Collectors.toList());
    }

    private List<MemberParliament> forVoteResult(List<MemberParliament> data, Vote vote, String result) {
        List<MemberParliament> list =  Stream.of(data).filter(member -> {
            for (Ballot ballot: vote.getBallots()) {
                if (ballot.getName().matches(member.getName())){
                    return ballot.getVote().matches(result);
                }
            }
            return false;
        }).collect(Collectors.toList());
        return list;
    }

    /** Check if filter will return only one object. Useful for performance reasons
     *
     * @return
     */
    public boolean isForOne() {
        return filters != null ? filters.containsKey(FilterParameters.FOR_ONE) : false;
    }

    /** Check if filter contains a url in order to bypass filter
     *
     * @return
     */
    public boolean containsUrl() {
        return filters != null ? filters.containsKey(FilterParameters.URL) : false;
    }



    @Override
    public void fromBundle(Bundle bundle) {
//        String name = bundle.getString("name");
//        if (name != null) {
//            addConstraint(FilterParameters.NAME, name);
//
//        }
//
//        String government = bundle.getString("government");
//        if (government != null) {
//            addConstraint(FilterParameters.GOVERNMENT, government);
//        }
        for (String key: bundle.keySet()) {
            addConstraint(key, bundle.get(key).toString());
        }
    }
}
