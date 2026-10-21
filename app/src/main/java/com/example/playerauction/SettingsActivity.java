
package com.example.playerauction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    EditText settingBidInput, settingTimeInput;
    Button settingSaveButton;
    TextView settingResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingBidInput = findViewById(R.id.settingBidInput);
        settingTimeInput = findViewById(R.id.settingTimeInput);
        settingSaveButton = findViewById(R.id.settingSaveButton);
        settingResult = findViewById(R.id.settingResult);

        // Display previously saved settings
        if (AppData.bidIncrement > 0) {
            settingBidInput.setText(String.valueOf(AppData.bidIncrement));
        }
        if (AppData.auctionDuration > 0) {
            settingTimeInput.setText(String.valueOf(AppData.auctionDuration));
        }
        showSettings();
        settingSaveButton.setOnClickListener(v -> saveSettings());
    }

    private void saveSettings() {
        String bidText = settingBidInput.getText().toString().trim();
        String timeText = settingTimeInput.getText().toString().trim();

        if (bidText.isEmpty() || timeText.isEmpty()) {
            Toast.makeText(this,"Enter both auction settings",Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double bid = Double.parseDouble(bidText);
            int duration = Integer.parseInt(timeText);

            if (bid <= 0 || duration <= 0) {
                Toast.makeText(this,"Both values must be greater than 0",Toast.LENGTH_SHORT).show();
                return;
            }

            // Save settings for AuctionActivity
            AppData.bidIncrement = bid;
            AppData.auctionDuration = duration;
            showSettings();

            Toast.makeText(this,"Settings saved successfully",Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this,"Enter valid numbers",Toast.LENGTH_SHORT).show();
        }
    }
    private void showSettings() {
        if (AppData.bidIncrement > 0 &&
                AppData.auctionDuration > 0) {
            settingResult.setText(
                    "Bid Increment: ₹" + AppData.bidIncrement +
                            "\nAuction Duration: " +
                            AppData.auctionDuration + " minutes"
            );
        } else {
            settingResult.setText("Enter your auction settings.");
        }
    }
}