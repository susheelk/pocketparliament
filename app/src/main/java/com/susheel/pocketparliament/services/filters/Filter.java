package com.susheel.pocketparliament.services.filters;

import java.util.EnumMap;
import java.util.List;

/**
 * @author Susheel Kona
 * Creds to whoever gave me this
 */

public abstract class Filter<T> {
    final EnumMap<FilterType, String> filters;

    public Filter(EnumMap<FilterType, String> filters) {
        this.filters = filters;
    }

    // No Filters
    public Filter() {
        filters = new EnumMap<FilterType, String>(FilterType.class);
    }

    public void addConstraint(FilterType type, String value) {
        filters.put(type, value);
    }

    public abstract List<T> doFilter(List<T> initialData);
}
