
package com.example.playerauction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity {

    EditText etPlayerName, etBasePrice;
    Button btAddPlayer;
    TextView txtPlayers;

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
                addPlayer();
            }
        });
        displayPlayers();
    }
    private void addPlayer() {
        String name = etPlayerName.getText().toString().trim();
        String priceText = etBasePrice.getText().toString().trim();

        if (name.isEmpty() || priceText.isEmpty()) {
            Toast.makeText(this,"Enter player name and base price",Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double basePrice = Double.parseDouble(priceText);
            if (basePrice <= 0) {
                Toast.makeText(this,"Base price must be greater than 0",Toast.LENGTH_SHORT).show();
                return;
            }
            Player player = new Player(name, basePrice);
            AppData.players.add(player);
            displayPlayers();
            etPlayerName.setText("");
            etBasePrice.setText("");

            Toast.makeText(this,"Player added successfully",Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this,"Enter a valid base price",Toast.LENGTH_SHORT).show();
        }
    }
    private void displayPlayers() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < AppData.players.size(); i++) {
            Player player = AppData.players.get(i);
            result.append(i + 1)
                    .append(". ")
                    .append(player.name)
                    .append("\nBase Price: ₹")
                    .append(player.basePrice)
                    .append("\n\n");
        }
        if (result.length() == 0) {
            txtPlayers.setText("No players added yet.");
        } else {
            txtPlayers.setText(result.toString());
        }
    }
}
