package com.example.grissgarcia.letraaletra;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Griss Garcia on 28/10/2015.
 */
public class PlayActivity extends Activity {

    private List<ParseObject> playItems;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private TextView clue;
    private int levelGame;
    private int win;
    private DBController dbController;
    private int ptos;
    private String correctWord;

    private List<Button> lettersButton;

    private TextView word;
    private TextView score;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_layout);
        dbController = DBController.getInstance();
        levelGame = 0;
        win = 0;
        ptos = 0;
        lettersButton = new ArrayList<>();
        initializeComponents();

        playItems = new ArrayList<>();
        try {
            getPlayItems();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setPlayItem();
        inflater = this.getLayoutInflater();
    }

    private void initializeComponents() {
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        clue = (TextView) findViewById(R.id.pista_txt);
        score = (TextView) findViewById(R.id.puntaje_txt);

        word = (TextView) findViewById(R.id.insert_word_txt);

        Button letter1 = (Button) findViewById(R.id.button1);
        Button letter2 = (Button) findViewById(R.id.button2);
        Button letter3 = (Button) findViewById(R.id.button3);
        Button letter4 = (Button) findViewById(R.id.button4);
        Button letter5 = (Button) findViewById(R.id.button5);
        Button letter6 = (Button) findViewById(R.id.button6);
        Button letter7 = (Button) findViewById(R.id.button7);
        Button letter8 = (Button) findViewById(R.id.button8);
        Button letter9 = (Button) findViewById(R.id.button9);
        Button letter10 = (Button) findViewById(R.id.button10);
        Button letter11 = (Button) findViewById(R.id.button11);

        lettersButton.add(letter1);
        lettersButton.add(letter2);
        lettersButton.add(letter3);
        lettersButton.add(letter4);
        lettersButton.add(letter5);
        lettersButton.add(letter6);
        lettersButton.add(letter7);
        lettersButton.add(letter8);
        lettersButton.add(letter9);
        lettersButton.add(letter10);
        lettersButton.add(letter11);
    }

    public void insertLetter(View view) {
        Button btn = (Button) view;
        String currentLetter = btn.getText().toString();
        word.setText(word.getText().toString().concat(currentLetter));
    }

    public void removeLetter(View view) {
        String newWord = "";
        if (word.getText().toString().length() != 0) {
            String wordToEdit = word.getText().toString();
            newWord = wordToEdit.substring(0, wordToEdit.length() - 1);
        }
        word.setText(newWord);
    }

    public void goToNextLevel(View view) {
        if(levelGame < playItems.size() - 1) {
            levelGame++;
            clue.clearComposingText();
            verifyCorrectWord(word.getText().toString());
            word.setText("");
            setPlayItem();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Felicidades");
            alertDialog.setMessage("Acaba de terminar todos los acertijos, y de seguro que ahora es una persona mas atenta " +
                    "pronto subiremos mas juegos, gracias por la preferencia");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                    levelGame = 0;
                    startActivity(intent);
                    finish();
                }
            });
            clearGame();
            alertDialog.setIcon(R.mipmap.trofeo);
            alertDialog.show();
        }
    }

    private void clearGame() {
        levelGame = 0;
        win = 0;
        ptos = 0;
    }

    private void verifyCorrectWord(String verifyWord) {
        if (correctWord.equals(verifyWord)) {
            win++;
            ptos = ptos + 107;
            score.setText("Puntaje " + ptos + "   ganados " + win + "   nivel " + levelGame);
        } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
                View view = inflater.inflate(R.layout.message_player_layout, null);
                final EditText playerNameInsert = (EditText) view.findViewById(R.id.player_name_txt);
                builder.setView(view);
                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String player = playerNameInsert.getText().toString();
                        doSave(player, ptos + "");
                        Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                        levelGame = 0;
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
        }
    }

    private void doSave(String playerName, String playerScore) {
        if (playerName.equals("")) {
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Ingrese su nombre", Toast.LENGTH_SHORT);

            toast1.show();
        } else {
            DBplayer dataBasePlayer = new DBplayer();
            dataBasePlayer.playerName = playerName;
            dataBasePlayer.score = playerScore;
            dbController.addPlayer(dataBasePlayer);
        }
    }

    private void getPlayItems() throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PlayItem");
        playItems = query.find();
    }

    private void setPlayItem() {
        System.out.println("playItems size = " + playItems.size());
        ParseObject item = playItems.get(levelGame);
        ParseFile file1 = item.getParseFile("image1");
        ParseFile file2 = item.getParseFile("image2");
        ParseFile file3 = item.getParseFile("image3");
        ParseFile file4 = item.getParseFile("image4");
        correctWord = item.getString("palabra");
        setClue();
        setLetters(item.getString("letters"));
        Picasso.with(getApplicationContext()).load(file1.getUrl()).into(image1);
        Picasso.with(getApplicationContext()).load(file2.getUrl()).into(image2);
        Picasso.with(getApplicationContext()).load(file3.getUrl()).into(image3);
        Picasso.with(getApplicationContext()).load(file4.getUrl()).into(image4);
    }

    private void setLetters(String letters) {
        for (int i = 0; i < letters.length(); i++) {
            char letter = letters.charAt(i);
            lettersButton.get(i).setText(letter + "");
        }
    }

    private void setClue() {
        StringBuilder builder = new StringBuilder();
        builder.append("Pista : La palabra contiene ");
        builder.append(correctWord.length());
        builder.append(" de estas letras");
        clue.setText(builder.toString());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Felicidades");
            alertDialog.setMessage("Esta seguro?? Perdera su avance...");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                    levelGame = 0;
                    startActivity(intent);
                    finish();
                }
            });
            alertDialog.setIcon(R.mipmap.trofeo);
            alertDialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
