
package com.example.playerauction;

public class AuctionRecord {
    String playerName;
    String teamName;
    double finalBid;
    String status;

    public AuctionRecord(String playerName, String teamName,double finalBid, String status) {
        this.playerName = playerName;
        this.teamName = teamName;
        this.finalBid = finalBid;
        this.status = status;
    }
}
