package com.example.grissgarcia.letraaletra;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Griss Garcia on 27/10/2015.
 */
public class MainActivity extends AppCompatActivity {

    private FloatingActionButton playBtn;
    private FloatingActionButton helpBtn;
    private FloatingActionButton winnerBtn;
    private FloatingActionButton adBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        playBtn = (FloatingActionButton) findViewById(R.id.play_btn);
        helpBtn = (FloatingActionButton) findViewById(R.id.help_btn);
        winnerBtn = (FloatingActionButton) findViewById(R.id.winner_btn);
        adBtn = (FloatingActionButton) findViewById(R.id.description);
        playBtn.setBackgroundTintList(getResources().getColorStateList(R.color.btn_color));
        helpBtn.setBackgroundTintList(getResources().getColorStateList(R.color.btn_color));
        winnerBtn.setBackgroundTintList(getResources().getColorStateList(R.color.btn_color));
        adBtn.setBackgroundTintList(getResources().getColorStateList(R.color.btn_color));
    }

    public void play(View view) {
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        startActivity(intent);
        finish();

    }

    public void help(View view) {
        Intent intent = new Intent(MainActivity.this, HelpActivity.class);
        startActivity(intent);
        finish();
    }

    public void showScore(View view) {
        DBController.getInstance().getPlayers(new ScoreInterface() {
            @Override
            public void getScores(List<DBplayer> scores) {
                final List<String> scoreToShow = new ArrayList<>();
                for(DBplayer player : scores) {
                    scoreToShow.add(player.getPlayerName() + " " + player.getScore());
                }
                String[] allNames = new String[scores.size()];
                for(int i = 0; i < scoreToShow.size(); i++) {
                    allNames[i] = scoreToShow.get(i);
                }

                Intent intent = new Intent(MainActivity.this, ScoreGame.class);
                intent.putExtra("playersName", allNames);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
