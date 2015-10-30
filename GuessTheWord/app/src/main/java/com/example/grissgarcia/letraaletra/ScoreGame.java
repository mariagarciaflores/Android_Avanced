package com.example.grissgarcia.letraaletra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Griss Garcia on 29/10/2015.
 */
public class ScoreGame extends Activity {

    private List<String> list;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_layout);
        list = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        updateList();
    }

    private void updateList() {
        Intent i = getIntent();
        String[] playerNames = i.getStringArrayExtra("playersName");
        for (int j = 0; j < playerNames.length; j++) {
            list.add(playerNames[j]);
        }
        adapter = new ScoreListAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
