package com.bankaccount.api.enums;

public enum Transaction {

    DEPOSIT("Deposit"), WITHDRAWAL("Withdrawal");

    private String trans ;

    private Transaction(String trans) {
        this.trans = trans ;
    }

    public String getTransaction() {
        return  this.trans ;
    }
}