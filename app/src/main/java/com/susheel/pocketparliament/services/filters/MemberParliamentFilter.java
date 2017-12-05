package com.susheel.pocketparliament.services.filters;

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
}
