package tech.susheelkona.pocketparliament.services.filters;

import android.os.Bundle;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Susheel Kona
 * Creds to whoever gave me this
 */

public abstract class Filter<T> {
    Map<String, Object> filters = new HashMap<>();


    public Filter(Map<String, Object> filters) {
        if (filters != null){
            this.filters = filters;
        } else {
            this.filters = new HashMap<>();
        }
    }

    // No Filters
    public Filter() {
        filters = new HashMap<>();
    }

    public void addConstraint(String type, Object value) {
        filters.put(type, value);
    }

    public abstract List<T> doFilter(List<T> initialData);

    public abstract void fromBundle(Bundle bundle);

    public Object getConstraint(String key) {
        return filters.get(key);
    }

//    public List<String> getFilterKeys() {
//        // Goddamn android doesn't let me use lambdas
//        List<String> keys = new ArrayList<String>();
//        for ()
//            new Bundle()
//    }


}
