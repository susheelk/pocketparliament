package com.susheel.pocketparliament.services;

import com.susheel.pocketparliament.model.legislation.Bill;
import com.susheel.pocketparliament.services.parsers.BillParser;

import java.util.List;

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


}
