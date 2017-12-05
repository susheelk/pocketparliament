package com.susheel.pocketparliament.model;

/**
 * @author Susheel
 */

public class Riding {

    public static Riding forList(final String name, final String province) {
        return new Riding() {{
            setName(name);
            setName(province);
        }};
    }

    private int id;
    private String province;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
