package com.example.playerauction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    EditText settingBidInput;
    EditText settingTimeInput;

    Button settingSaveButton;

    TextView settingResult;

    double bidIncrement;
    int auctionDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingBidInput=findViewById(R.id.settingBidInput);
        settingTimeInput=findViewById(R.id.settingTimeInput);
        settingSaveButton=findViewById(R.id.settingSaveButton);
        settingResult=findViewById(R.id.settingResult);
        settingSaveButton.setOnClickListener(v -> saveSettings());
    }
    private void saveSettings() {
        String bidText = settingBidInput.getText().toString().trim();
        String timeText = settingTimeInput.getText().toString().trim();

        if (bidText.isEmpty()) {
            Toast.makeText(this,"Enter bid increment",Toast.LENGTH_SHORT).show();
            return;
        }
        if (timeText.isEmpty()) {
            Toast.makeText(this,"Enter auction duration",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            bidIncrement = Double.parseDouble(bidText);
            auctionDuration = Integer.parseInt(timeText);
        } catch (NumberFormatException e) {
            Toast.makeText(this,"Enter valid numbers",Toast.LENGTH_SHORT).show();
            return;
        }

        if (bidIncrement <= 0) {
            Toast.makeText(this,"Bid increment must be greater than 0",Toast.LENGTH_SHORT).show();
            return;
        }

        if (auctionDuration <= 0) {
            Toast.makeText(this,"Auction duration must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        settingResult.setText("Bid Increment: ₹" + bidIncrement + "\nAuction Duration: " + auctionDuration + " minutes");
        Toast.makeText(this,"Settings saved successfully",Toast.LENGTH_SHORT).show();
    }
}