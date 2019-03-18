package com.example.budgetapplication.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.budgetapplication.R;
import com.example.budgetapplication.VideoAdapter;
import com.example.budgetapplication.YouTubeVideos;

import java.util.Vector;

public class SearchytFragment extends Fragment {

    private View exampleView;
    private RecyclerView recyclerView;
    private Vector<YouTubeVideos> youTubeVideos = new Vector<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        exampleView = inflater.inflate(R.layout.fragment_searchyt, container, false);

        recyclerView = exampleView.findViewById(R.id.ytRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));

        String czek = "check";
        if(getString(R.string.check) == czek) {

            youTubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/XZjxaw9TH48\" frameborder=\"0\"  allowfullscreen></iframe>"));
            youTubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/wJB90G-tsgo\" frameborder=\"0\"  allowfullscreen></iframe>"));
            youTubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/iIt_MzyKaac\" frameborder=\"0\"  allowfullscreen></iframe>"));
            youTubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/Oi9cq7tXkmg\" frameborder=\"0\"  allowfullscreen></iframe>"));
            youTubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/S6HEH23W_bM\" frameborder=\"0\"  allowfullscreen></iframe>"));

        } else {
            youTubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/2Wa5SUHf3gw\" frameborder=\"0\"  allowfullscreen></iframe>"));
            youTubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/jcUas6dZTpc\" frameborder=\"0\"  allowfullscreen></iframe>"));
            youTubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/58N8X0FOAPU\" frameborder=\"0\"  allowfullscreen></iframe>"));
            youTubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/aUJwgpWGeig\" frameborder=\"0\"  allowfullscreen></iframe>"));
            youTubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/tYQUUm9HTK0\" frameborder=\"0\"  allowfullscreen></iframe>"));
            youTubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/zJcUOM9wXvo\" frameborder=\"0\"  allowfullscreen></iframe>"));
        }

        VideoAdapter videoAdapter = new VideoAdapter(youTubeVideos);

        recyclerView.setAdapter(videoAdapter);

        return exampleView;
    }

}
