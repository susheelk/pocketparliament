package com.susheel.pocketparliament.services.filters;

import android.os.Bundle;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.susheel.pocketparliament.model.MemberParliament;

import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * @author Susheel Kona
 */
// TODO implement mp filter
public class MemberParliamentFilter extends Filter<MemberParliament> {

    public MemberParliamentFilter(Map<String, Object> filters) {
        super(filters);
    }

    public MemberParliamentFilter(){}

    @Override
    public List<MemberParliament> doFilter(List<MemberParliament> data) {
//        System.out.println("Initiated Filters");
        if (filters != null) {
            for(Map.Entry<String, Object> entry: filters.entrySet()) {
                switch (entry.getKey()) {
                    case FilterParameters.GROUP: data = group(data, entry.getValue()); break;
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
        }
        return data;
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
