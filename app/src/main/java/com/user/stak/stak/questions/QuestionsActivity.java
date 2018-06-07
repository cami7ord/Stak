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

        String[] options = new String[4];
        options[0] = "Uno";
        options[1] = "Dos";
        options[2] = "Tres";
        options[3] = "Cuatro";

        List<Question> questions = new ArrayList<Question>();
        questions.add(new BinaryQuestion("binary", "Binary 1", false));
        questions.add(new BinaryQuestion("binary", "Binary 2", true));
        questions.add(new NumericalQuestion("numerical", "Numerical 1", 1, 0, 100));
        questions.add(new NumericalQuestion("numerical", "Numerical 2", 2, 0, 100));
        questions.add(new SingleResponseQuestion("single_response", "SResponse 1", 1, options));
        questions.add(new SingleResponseQuestion("single_response", "SResponse 2", 2, options));

        mAdapter = new QuestionsListAdapter(questions);
        mRecyclerView.setAdapter(mAdapter);
    }
}
