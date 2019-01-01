package tech.susheelkona.pocketparliament.model.legislation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Vote {

    private int id;
    private Date date;
    private String title;
    private String billUrl;
    private int billId;
    private String description;
    private int yeas;
    private int nays;
    private String result;

    private List<Ballot> ballots;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBillUrl() {
        return billUrl;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public void setBillUrl(String billUrl) {
        this.billUrl = billUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYeas() {
        return yeas;
    }

    public void setYeas(int yeas) {
        this.yeas = yeas;
    }

    public int getNays() {
        return nays;
    }

    public void setNays(int nays) {
        this.nays = nays;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Ballot> getBallots() {
        return ballots;
    }

    public void setBallots(List<Ballot> ballots) {
        this.ballots = ballots;
    }

//    public List<Ballot> getBallots() {
//        return ballots;
//    }
//
//    public void setBallots(List<Ballot> ballots) {
//        this.ballots = ballots;
//    }
}
