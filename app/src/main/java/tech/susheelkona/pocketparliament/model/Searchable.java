package tech.susheelkona.pocketparliament.model;

public interface Searchable {
    boolean contains(String query); // TODO: Use fuzzywuzzy
    String getFlattened();
}
