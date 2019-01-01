package tech.susheelkona.pocketparliament.services;

import tech.susheelkona.pocketparliament.services.parsers.BillParser;
import tech.susheelkona.pocketparliament.services.parsers.VoteParser;
import tech.susheelkona.pocketparliament.model.legislation.Bill;
import tech.susheelkona.pocketparliament.model.legislation.Vote;

import java.util.List;

public class BillService extends HttpService {
    private static final BillService instance = new BillService();
    public static BillService getInstance(){
        return instance;
    }


    public List<Bill> get(String params) throws Exception{
        String response = doRequest(BILLSEARCH, "bills"+params);
        List<Bill> list = BillParser.getInstance().listFromJson(response);
        return list;
    }

    public Bill getById(int id) throws Exception {
        String response = doRequest(BILLSEARCH, "bills/"+id);
        return BillParser.getInstance().objectFromJson(response);
    }

    public List<Vote> getVotes(String params) throws Exception {
        String response = doRequest(BILLSEARCH, "votes"+params);
        return VoteParser.getInstance().listFromJson(response);
    }

    public Vote getVote(int id) throws Exception {
        String response = doRequest(BILLSEARCH, "votes/"+id);
        return VoteParser.getInstance().objectFromJson(response);
    }

//    public List<Vote> getVotesByBill(String session, String bill) throws Exception {
//        String respone = doRequest(HttpService.OPEN_PARL, "votes/"+"bill=/bills/"+session+"/"+bill);
//
//    }


}
