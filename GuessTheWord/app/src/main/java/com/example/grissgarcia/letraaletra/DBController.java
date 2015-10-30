package com.example.grissgarcia.letraaletra;

import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Griss Garcia on 29/10/2015.
 */
public class DBController {

    private static DBController instance;

    public static DBController getInstance() {
        if (instance == null) {
            instance = new DBController();
        }
        return instance;
    }

    public void addPlayer(DBplayer player) {
        player.saveLocally();
    }

    private void getPlayerLocally(ScoreInterface callback) {
        List<DBplayer> players = new Select()
                .from(DBplayer.class).execute();
                callback.getScores(players);
    }

    public void getPlayers(ScoreInterface callback) {
        getPlayerLocally(callback);
    }
}
