package com.example.grissgarcia.letraaletra;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Griss Garcia on 29/10/2015.
 */

@Table(name = "WinnerTable")
public class DBplayer extends Model {
    @Column(name = "playerName")
    public String playerName;
    @Column (name = "score")
    public String score;

    public DBplayer() {
        super();
    }

    public String getPlayerName(){
        return playerName;
    }

    public String getScore() {
        return score;
    }

    public void saveLocally() {
        save();
    }
}
