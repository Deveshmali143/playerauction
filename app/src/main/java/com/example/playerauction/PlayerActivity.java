package com.example.playerauction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    EditText etPlayerName;
    EditText etBasePrice;
    Button btAddPlayer;
    TextView txtPlayers;
    ArrayList<Player> players = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        etPlayerName = findViewById(R.id.etPlayerName);
        etBasePrice = findViewById(R.id.etBasePrice);
        btAddPlayer = findViewById(R.id.btAddPlayer);
        txtPlayers = findViewById(R.id.txtPlayers);

        btAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etPlayerName.getText().toString().trim();
                String priceText = etBasePrice.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(PlayerActivity.this,"Enter player name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (priceText.isEmpty()) {
                    Toast.makeText(PlayerActivity.this,"Enter base price",Toast.LENGTH_SHORT).show();
                    return;
                }
                double basePrice = Double.parseDouble(priceText);
                if (basePrice <= 0) {
                    Toast.makeText(PlayerActivity.this, "Base price must be greater than 0",Toast.LENGTH_SHORT).show();
                    return;
                }
                Player player = new Player(name, basePrice);
                players.add(player);
                displayPlayers();
                etPlayerName.setText("");
                etBasePrice.setText("");
                Toast.makeText(PlayerActivity.this,"Player added successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void displayPlayers() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            result.append(i + 1)
                    .append(". ")
                    .append(player.name)
                    .append("\nBase Price: ₹")
                    .append(player.basePrice)
                    .append("\n\n");
        }
        txtPlayers.setText(result.toString());
    }
}