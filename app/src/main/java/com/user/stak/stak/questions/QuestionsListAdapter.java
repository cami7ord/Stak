package com.user.stak.stak.questions;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.user.stak.stak.R;
import com.user.stak.stak.model.Question;

import java.util.List;

public class QuestionsListAdapter extends RecyclerView.Adapter<QuestionsListAdapter.ViewHolder> {

    private List<Question> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView questionTitle;
        public TextView questionAnswer;

        public ViewHolder(View v) {
            super(v);
            questionTitle = v.findViewById(R.id.question_title);
            questionAnswer = v.findViewById(R.id.question_answer);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public QuestionsListAdapter(List<Question> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_question, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.questionTitle.setText(mDataset.get(position).getTitle());
        holder.questionAnswer.setText(mDataset.get(position).getAnswer().toString());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
