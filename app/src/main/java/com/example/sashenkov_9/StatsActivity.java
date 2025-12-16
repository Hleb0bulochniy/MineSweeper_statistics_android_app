package com.example.sashenkov_9;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sashenkov_9.DTO.MapSaveListModelDTO;
import com.example.sashenkov_9.DTO.MapSaveModelDTO;
import com.example.sashenkov_9.Help.MapsStorage;

public class StatsActivity extends AppCompatActivity {

    Button backButton;
    TextView statGames;
    TextView statWins;
    TextView statLoses;
    TextView statOpenedTiles;
    TextView statOpenedNumbers;
    TextView statOpenedBlank;
    TextView statFlagsTotal;
    TextView statFlagsOnBombs;
    TextView statTimeTotal;
    TextView statTimeAverage;
    public static final String EXTRA_MAP_ID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stats);

        backButton = findViewById(R.id.stat_back_button);
        statGames = findViewById(R.id.stat_games);
        statWins = findViewById(R.id.stat_wins);
        statLoses = findViewById(R.id.stat_loses);
        statOpenedTiles = findViewById(R.id.stat_opened_tiles);
        statOpenedNumbers = findViewById(R.id.stat_opened_numbers);
        statOpenedBlank = findViewById(R.id.stat_opened_blank);
        statFlagsTotal = findViewById(R.id.stat_flags_total);
        statFlagsOnBombs = findViewById(R.id.stat_flags_on_bombs);
        statTimeTotal = findViewById(R.id.stat_time_total);
        statTimeAverage = findViewById(R.id.stat_time_avg);

        backButton.setOnClickListener(this::SetMainActivity);

        int mapId = getIntent().getIntExtra(EXTRA_MAP_ID, -1);
        if (mapId == -1) {
            finish();
            return;
        }
        SetInfoAboutMap(mapId);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void SetInfoAboutMap(int mapId){
        MapSaveListModelDTO list = MapsStorage.get(getApplicationContext());
        if (list == null || list.getMapSaveList() == null) {
            finish();
            return;
        }

        MapSaveModelDTO map = null;
        for (MapSaveModelDTO m : list.getMapSaveList()) {
            if (m.getMapId() == mapId) {
                map = m;
                break;
            }
        }
        if (map == null) {
            finish();
            return;
        }

        statGames.setText("Games total: " + map.getGamesSum());
        statWins.setText("Wins: " + map.getWins());
        statLoses.setText("Loses: " + map.getLoses());
        statOpenedTiles.setText("Opened Tiles Total: " + map.getOpenedTiles());
        statOpenedNumbers.setText("Opened Number Tiles: " + map.getOpenedNumberTiles());
        statOpenedBlank.setText("Opened Blank Tiles: " + map.getOpenedBlankTiles());
        statFlagsTotal.setText("Flags Placed: " + map.getFlagsSum());
        statFlagsOnBombs.setText("Bombs Defused: " + map.getFlagsOnBombs());
        statTimeTotal.setText("Time Spent Total: " + map.getTimeSpentSum());
        statTimeAverage.setText("Average Game Time: " + (map.getGamesSum() > 0 ? (map.getTimeSpentSum() / map.getGamesSum()) : 0));

    }

    private void SetMainActivity(View v){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}