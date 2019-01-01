package com.susheel.pocketparliament.services.filters;

/**
 *
 */

public interface FilterParameters {
    // Generic Stuff
    String FILTER = "filter";
    String ID = "id";
    String NAME = "name";
    String FOR_ONE = "for_one";
    String URL = "url";
    String QUERY = "query";

    String FOLLOWED = "followed";

    String FOLLOWING_FIRST = "following_first";

    String VOTE_ID = "vote_id";
    String RESULT = "result";

    String VOTED_FOR = "voted_for";
    String VOTED_AGAINST = "voted_against";

    // MemberParliament Filters
    String GROUP = "group";
    String GOVERNMENT = "govt";
    String OPPOSITION = "opp";
    String CABINET = "cabinet";
    String PARTY = "party";
    String MEMBER_PARLIAMENT = "mp";
    String VOTE = "vote";

    // Party Filters
    String COLOR = "color";




}