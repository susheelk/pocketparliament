package com.susheel.pocketparliament.services.filters;

import android.os.Bundle;

import com.susheel.pocketparliament.model.MemberParliament;

import java.util.List;


/**
 * @author Susheel Kona
 */
// TODO implement mp filter
public class MemberParliamentFilter extends Filter<MemberParliament> {
    @Override
    public List<MemberParliament> doFilter(List<MemberParliament> data) {
        return data; // Does not filter currently
    }

    @Override
    public void fromBundle(Bundle bundle) {
//        String name = bundle.getString("name");
//        if (name != null) {
//            addConstraint(FilterType.NAME, name);
//
//        }
//
//        String government = bundle.getString("government");
//        if (government != null) {
//            addConstraint(FilterType.GOVERNMENT, government);
//        }
        for (String key: bundle.keySet()) {
            addConstraint(key, bundle.get(key).toString());
        }
    }
}
