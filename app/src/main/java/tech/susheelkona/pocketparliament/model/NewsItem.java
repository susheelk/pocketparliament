package tech.susheelkona.pocketparliament.model;

public class NewsItem {
    private String title;
    private String billNumber;
    private String url;
    private String date;
    private String description;
    protected String tagline;
    private int billId;

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void generateTagline() {
        setTagline(billNumber+", "+title);
    }

    @Override
    public boolean equals(Object obj) {
        return ((NewsItem)obj).getTagline().matches(getTagline());
    }
}