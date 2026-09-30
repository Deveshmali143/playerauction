package com.example.playerauction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {

    EditText etTeamName;
    EditText etPurse;
    Button btAddTeam;
    TextView txtTeams;
    ArrayList<team> teams = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        etTeamName = findViewById(R.id.etTeamName);
        etPurse = findViewById(R.id.etPurse);
        btAddTeam = findViewById(R.id.btAddTeam);
        txtTeams = findViewById(R.id.txtTeams);

        btAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etTeamName.getText().toString().trim();
                String purseText = etPurse.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(TeamActivity.this, "Enter team name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (purseText.isEmpty()) {
                    Toast.makeText(TeamActivity.this, "Enter starting purse", Toast.LENGTH_SHORT).show();
                    return;
                }
                double purse = Double.parseDouble(purseText);
                if (purse <= 0) {
                    Toast.makeText(TeamActivity.this, "Purse must be greater than 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                team team = new team(name,purse);
                teams.add(team);
                displayTeams();
                etTeamName.setText("");
                etPurse.setText("");
                Toast.makeText(TeamActivity.this, "Team added successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void displayTeams() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < teams.size(); i++) {
            team team = teams.get(i);
            result.append(i + 1)
                    .append(". ")
                    .append(team.name)
                    .append("\nPurse: ₹")
                    .append(team.purse)
                    .append("\n\n");
        }
        txtTeams.setText(result.toString());
    }
}