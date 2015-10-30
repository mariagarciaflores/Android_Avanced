package com.example.grissgarcia.letraaletra;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Griss Garcia on 29/10/2015.
 */
public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ScoreViewHolder> {

    private List<String> playersScore;

    public ScoreListAdapter(List<String> playersScore) {
        this.playersScore = playersScore;
    }

    @Override
    public ScoreListAdapter.ScoreViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.score_item_layout, viewGroup, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScoreListAdapter.ScoreViewHolder scoreViewHolder, int i) {
        ScoreViewHolder.getPlayerScore().setText(playersScore.get(i));
    }

    @Override
    public int getItemCount() {
        return playersScore.size();
    }

    public static class ScoreViewHolder extends RecyclerView.ViewHolder {
        private static TextView playerScore;
        public ScoreViewHolder(View itemView) {
            super(itemView);
            playerScore = (TextView) itemView.findViewById(R.id.txt_score);
        }

        public static TextView getPlayerScore() {
            return playerScore;
        }
    }
}
