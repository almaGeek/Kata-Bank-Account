package com.bankaccount.api.enums;

public enum Account {

    COURANT("Courant"), EPARGNE("Epargne");

    private String type ;

    private Account(String type) {
        this.type = type ;
    }

    public String getType() {
        return  this.type ;
    }
}
