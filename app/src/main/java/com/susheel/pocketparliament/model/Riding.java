package com.susheel.pocketparliament.model;

/**
 * @author Susheel
 */

public class Riding {

    public static Riding forList(String name, String province) {
        Riding riding = new Riding();
        riding.setName(name);
        riding.setProvince(province);
        return riding;
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
