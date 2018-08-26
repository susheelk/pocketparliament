package com.susheel.pocketparliament.services;

import com.susheel.pocketparliament.model.legislation.Bill;
import com.susheel.pocketparliament.model.legislation.Vote;
import com.susheel.pocketparliament.services.parsers.BillParser;
import com.susheel.pocketparliament.services.parsers.VoteParser;

import java.util.List;
import java.util.stream.Stream;

public class BillService extends HttpService {
    private static final BillService instance = new BillService();
    public static BillService getInstance(){
        return instance;
    }


    public List<Bill> get(String params) throws Exception{
        String response = doRequest(HttpService.BILLSEARCH, "bills"+params);
        List<Bill> list = BillParser.getInstance().listFromJson(response);
        return list;
    }

    public Bill getById(int id) throws Exception {
        String response = doRequest(HttpService.BILLSEARCH, "bills/"+id);
        return BillParser.getInstance().objectFromJson(response);
    }

    public List<Vote> getVotes(String params) throws Exception {
        String response = doRequest(HttpService.BILLSEARCH, "votes"+params);
        return VoteParser.getInstance().listFromJson(response);
    }

    public Vote getVote(int id) throws Exception {
        String response = doRequest(HttpService.BILLSEARCH, "votes/"+id);
        return VoteParser.getInstance().objectFromJson(response);
    }

//    public List<Vote> getVotesByBill(String session, String bill) throws Exception {
//        String respone = doRequest(HttpService.OPEN_PARL, "votes/"+"bill=/bills/"+session+"/"+bill);
//
//    }


}
