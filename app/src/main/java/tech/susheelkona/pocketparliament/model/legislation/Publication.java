package tech.susheelkona.pocketparliament.model.legislation;

/**
 * @author Susheel Kona
 */

public class Publication {
    private String title;
    private int id;
    private String url;

    public Publication(String title, int id, String url) {
        this.title = title;
        this.id = id;
        this.url = url;
    }

    public Publication(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
