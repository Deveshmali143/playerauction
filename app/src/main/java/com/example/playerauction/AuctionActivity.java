package com.example.playerauction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AuctionActivity extends AppCompatActivity {

    TextView auctionPlayerName;
    TextView auctionBasePrice;
    TextView auctionCurrentBid;
    TextView auctionHighestTeam;
    TextView auctionTimer;
    TextView auctionTeamList;

    Button auctionBidButton;
    Button auctionSoldButton;
    Button auctionUnsoldButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auction);

        auctionPlayerName =
                findViewById(R.id.auctionPlayerName);

        auctionBasePrice =
                findViewById(R.id.auctionBasePrice);

        auctionCurrentBid =
                findViewById(R.id.auctionCurrentBid);

        auctionHighestTeam =
                findViewById(R.id.auctionHighestTeam);

        auctionTimer =
                findViewById(R.id.auctionTimer);

        auctionTeamList =
                findViewById(R.id.auctionTeamList);

        auctionBidButton =
                findViewById(R.id.auctionBidButton);

        auctionSoldButton =
                findViewById(R.id.auctionSoldButton);

        auctionUnsoldButton =
                findViewById(R.id.auctionUnsoldButton);

        auctionPlayerName.setText("No Player Selected");

        auctionBasePrice.setText("Base Price: ₹0");

        auctionCurrentBid.setText("Current Bid: ₹0");

        auctionHighestTeam.setText(
                "Highest Bid Team: None"
        );

        auctionTimer.setText("00:00");

        auctionTeamList.setText(
                "Teams will appear here"
        );
    }
}