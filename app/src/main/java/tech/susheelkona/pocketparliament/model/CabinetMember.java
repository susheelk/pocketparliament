package tech.susheelkona.pocketparliament.model;

public class CabinetMember extends MemberParliament {
    private String position;
    private int orderOfPrecedence;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getOrderOfPrecedence() {
        return orderOfPrecedence;
    }

    public void setOrderOfPrecedence(int orderOfPrecedence) {
        this.orderOfPrecedence = orderOfPrecedence;
    }

    @Override
    public String getBlurb() {
        return getPosition();
    }
}
