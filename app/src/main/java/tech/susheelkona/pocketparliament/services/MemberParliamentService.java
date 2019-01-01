package tech.susheelkona.pocketparliament.services;



import tech.susheelkona.pocketparliament.model.MemberParliament;
import tech.susheelkona.pocketparliament.services.parsers.MemberParliamentParser;


import java.util.List;


/**
 * @author Susheel Kona
 */

public class MemberParliamentService extends HttpService{
    private static final MemberParliamentService instance = new MemberParliamentService();
    public static MemberParliamentService getInstance(){
        return instance;
    }

    private MemberParliamentParser parser = MemberParliamentParser.getInstance();

    public List<MemberParliament> get() throws Exception{
        String response = doRequest(OPEN_PARL, "politicians");
        List<MemberParliament> data =  parser.listFromJson(response);

        return data;
    }

    /** This skips the filter to avoid double requests
     *
     * @param url
     * @return
     * @throws Exception
     */
    public MemberParliament getUniqueByUrl(String url) throws Exception{
        String response = doRequest(OPEN_PARL, url);
        return parser.objectFromJson(response);
    }

}
