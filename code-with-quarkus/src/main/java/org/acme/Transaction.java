package org.acme;

public class Transaction {

    int amount;
    String cid;
    String mid;

    public Transaction() {
        cid = "";
    }

    public Transaction(int amount,
                       String cid,
                       String mid) {
        this.amount = amount;
        this.cid = cid;
        this.mid = mid;
    }
}
