package com.example.xpfit;

public class User {

    public String uid;
    public String fname;
    public int xpscore, strengthxp, cardioxp, level, order, strengthorder, cardioorder;

    public User() {
    }

    public User(String uid, String fname, int xpscore, int strengthxp, int cardioxp, int level, int order, int strengthorder, int cardioorder) {
      this.uid = uid;
      this.fname = fname;
      this.xpscore = xpscore;
      this.strengthxp = strengthxp;
      this.cardioxp = cardioxp;
      this.level = level;
      this.order = order;
      this.strengthorder = strengthorder;
      this.cardioorder = cardioorder;
    }

    public int getStrengthorder() {
        return strengthorder;
    }

    public void setStrengthorder(int strengthorder) {
        this.strengthorder = strengthorder;
    }

    public int getCardioorder() {
        return cardioorder;
    }

    public void setCardioorder(int cardioorder) {
        this.cardioorder = cardioorder;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getXpscore() {
        return xpscore;
    }

    public void setXpscore(int xpscore) {
        this.xpscore = xpscore;
    }

    public int getStrengthxp() {
        return strengthxp;
    }

    public void setStrengthxp(int strengthxp) {
        this.strengthxp = strengthxp;
    }

    public int getCardioxp() {
        return cardioxp;
    }

    public void setCardioxp(int cardioxp) {
        this.cardioxp = cardioxp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
