package com.user.stak.stak.questions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.user.stak.stak.R;
import com.user.stak.stak.model.BinaryQuestion;
import com.user.stak.stak.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        mRecyclerView = findViewById(R.id.questions_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        List<Question> questions = new ArrayList<Question>();
        questions.add(new BinaryQuestion("binary", "Titulo", false));
        questions.add(new BinaryQuestion("binary", "Titulo2", true));
        questions.add(new BinaryQuestion("binary", "Titulo3", false));
        questions.add(new BinaryQuestion("binary", "Titulo4", true));

        mAdapter = new QuestionsListAdapter(questions);
        mRecyclerView.setAdapter(mAdapter);
    }
}
