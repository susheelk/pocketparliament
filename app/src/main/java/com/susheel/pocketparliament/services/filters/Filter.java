package com.susheel.pocketparliament.services.filters;

import android.os.Bundle;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Susheel Kona
 * Creds to whoever gave me this
 */

public abstract class Filter<T> {
    final Map<String, String> filters;

    public Filter(Map<String, String> filters) {
        this.filters = filters;
    }

    // No Filters
    public Filter() {
        filters = new HashMap<>();
    }

    public void addConstraint(String type, String value) {
        filters.put(type, value);
    }

    public abstract List<T> doFilter(List<T> initialData);

    public abstract void fromBundle(Bundle bundle);

//    public List<String> getFilterKeys() {
//        // Goddamn android doesn't let me use lambdas
//        List<String> keys = new ArrayList<String>();
//        for ()
//            new Bundle()
//    }


}
