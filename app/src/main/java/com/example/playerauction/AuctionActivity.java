
package com.example.playerauction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AuctionActivity extends AppCompatActivity {

    TextView auctionPlayerName;
    TextView auctionBasePrice;
    TextView auctionCurrentBid;
    TextView auctionHighestTeam;
    TextView auctionTimer;
    TextView auctionTeamList;

    Button auctionSoldButton;
    Button auctionUnsoldButton;

    LinearLayout auctionTeamButtons;

    int currentPlayerIndex = 0;
    double currentBid = 0;
    Team highestTeam = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction);

        auctionPlayerName = findViewById(R.id.auctionPlayerName);
        auctionBasePrice = findViewById(R.id.auctionBasePrice);
        auctionCurrentBid = findViewById(R.id.auctionCurrentBid);
        auctionHighestTeam = findViewById(R.id.auctionHighestTeam);
        auctionTimer = findViewById(R.id.auctionTimer);
        auctionTeamList = findViewById(R.id.auctionTeamList);

        auctionTeamButtons = findViewById(R.id.auctionTeamButtons);

        auctionSoldButton = findViewById(R.id.auctionSoldButton);
        auctionUnsoldButton = findViewById(R.id.auctionUnsoldButton);

        auctionSoldButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finishPlayer(true);
                    }
                });

        auctionUnsoldButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finishPlayer(false);
                    }
                });

        showCurrentPlayer();
    }
    private void showCurrentPlayer() {
        if (currentPlayerIndex >= AppData.players.size()) {
            auctionPlayerName.setText("Auction completed!");
            auctionBasePrice.setText("");
            auctionCurrentBid.setText("");
            auctionHighestTeam.setText("");
            auctionTimer.setText("All players processed.");
            StringBuilder history = new StringBuilder("FINAL AUCTION RECORDS\n\n");

            for (AuctionRecord record : AppData.auctionRecords) {
                history.append("Player: ")
                        .append(record.playerName)
                        .append("\nTeam: ")
                        .append(record.teamName)
                        .append("\nFinal Bid: ₹")
                        .append(record.finalBid)
                        .append("\nStatus: ")
                        .append(record.status)
                        .append("\n\n");
            }

            if (AppData.auctionRecords.isEmpty()) {
                history.append("No auction records available.");
            }

            auctionTeamList.setText(history.toString());
            auctionTeamButtons.removeAllViews();

            auctionSoldButton.setEnabled(false);
            auctionUnsoldButton.setEnabled(false);
            return;
        }

        Player player = AppData.players.get(currentPlayerIndex);

        currentBid = player.basePrice;
        highestTeam = null;

        auctionPlayerName.setText(player.name);
        auctionBasePrice.setText("Base Price: ₹" + player.basePrice);
        auctionCurrentBid.setText("Current Bid: ₹" + currentBid);
        auctionHighestTeam.setText("Highest Bid Team: None");

        auctionTimer.setText(
                "Duration: " + AppData.auctionDuration + " minutes");

        auctionSoldButton.setEnabled(false);
        auctionUnsoldButton.setEnabled(true);

        displayTeamButtons();
        displayTeams();
    }

    private void displayTeamButtons() {

        auctionTeamButtons.removeAllViews();

        if (AppData.teams.isEmpty()) {
            TextView message = new TextView(this);
            message.setText("Please add teams first.");
            auctionTeamButtons.addView(message);
            return;
        }

        for (final Team team : AppData.teams) {

            Button teamButton = new Button(this);

            teamButton.setText(
                    team.name + " | Purse: ₹" + team.purse);

            teamButton.setAllCaps(false);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(0, 8, 0, 8);

            auctionTeamButtons.addView(teamButton, params);

            teamButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            placeBid(team);
                        }
                    });
        }
    }

    private void placeBid(Team selectedTeam) {

        if (AppData.bidIncrement <= 0) {
            Toast.makeText(this,
                    "Set bid increment in Auction Settings",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedTeam == highestTeam) {
            Toast.makeText(this,
                    "Another team must place the next bid",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        double nextBid = currentBid + AppData.bidIncrement;

        if (selectedTeam.purse < nextBid) {
            Toast.makeText(this,
                    selectedTeam.name + " does not have enough purse",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        currentBid = nextBid;
        highestTeam = selectedTeam;

        auctionCurrentBid.setText("Current Bid: ₹" + currentBid);
        auctionHighestTeam.setText(
                "Highest Bid Team: " + highestTeam.name);

        auctionSoldButton.setEnabled(true);

        Toast.makeText(this,
                selectedTeam.name + " bids ₹" + currentBid,
                Toast.LENGTH_SHORT).show();

        displayTeamButtons();
    }

    private void finishPlayer(boolean sold) {

        if (currentPlayerIndex >= AppData.players.size()) {
            return;
        }

        Player player = AppData.players.get(currentPlayerIndex);

        if (sold) {

            if (highestTeam == null) {
                Toast.makeText(this,
                        "Place a bid before selling the player",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            highestTeam.purse -= currentBid;

            AppData.auctionRecords.add(new AuctionRecord(
                    player.name,
                    highestTeam.name,
                    currentBid,
                    "SOLD"
            ));

            Toast.makeText(this,
                    "SOLD to " + highestTeam.name
                            + " for ₹" + currentBid,
                    Toast.LENGTH_LONG).show();

        } else {

            AppData.auctionRecords.add(new AuctionRecord(
                    player.name,
                    "None",
                    0,
                    "UNSOLD"
            ));

            Toast.makeText(this,
                    "Player marked UNSOLD",
                    Toast.LENGTH_SHORT).show();
        }

        currentPlayerIndex++;
        showCurrentPlayer();
    }

    private void displayTeams() {

        StringBuilder result =
                new StringBuilder("Teams and remaining purses:\n\n");

        for (Team team : AppData.teams) {
            result.append(team.name)
                    .append(" - ₹")
                    .append(team.purse)
                    .append("\n");
        }

        if (AppData.teams.isEmpty()) {
            result.append("No teams added yet.");
        }

        auctionTeamList.setText(result.toString());
    }
}
