package com.user.stak.stak.questions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.user.stak.stak.R;
import com.user.stak.stak.model.BinaryQuestion;
import com.user.stak.stak.model.NumericalQuestion;
import com.user.stak.stak.model.Question;
import com.user.stak.stak.model.SingleResponseQuestion;

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

        String[] options = new String[5];
        options[0] = "Casado";
        options[1] = "Soltero";
        options[2] = "Divorciado";
        options[3] = "Viudo";
        options[4] = "Unión libre";

        List<Question> questions = new ArrayList<>();
        questions.add(new BinaryQuestion("binary", "¿Eres mayor de edad?", false));
        questions.add(new SingleResponseQuestion("single_response", "¿Estado civil?", 1, options));
        questions.add(new NumericalQuestion("numerical", "¿Cuál es tu edad?", 27, 0, 100));

        mAdapter = new QuestionsListAdapter(questions);
        mRecyclerView.setAdapter(mAdapter);
    }
}
