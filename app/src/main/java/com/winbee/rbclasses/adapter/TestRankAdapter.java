package com.winbee.rbclasses.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.rbclasses.NewModels.TestTopRankerArray;
import com.winbee.rbclasses.R;

import java.util.List;

public class TestRankAdapter extends RecyclerView.Adapter<TestRankAdapter.CustomViewHolder> {
    private Context context;
    private List<TestTopRankerArray> testTopRankerArrays;

    public TestRankAdapter(Context context, List<TestTopRankerArray> testTopRankerArrays) {
        this.context=context;
        this.testTopRankerArrays = testTopRankerArrays;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_test_rank_adapter,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, final int position) {
//        final SectionDetailsDataModel sectionDetailsDataModel = sectionDetailsDataModelList.get(position);

        viewHolder.txt_name.setText(testTopRankerArrays.get(position).getName());
        viewHolder.txt_marks.setText(testTopRankerArrays.get(position).getTotalScore());
        viewHolder.txt_rank.setText(Integer.toString(testTopRankerArrays.get(position).getRank()));
    }
    @Override
    public int getItemCount() {
        return testTopRankerArrays==null ? 0 : testTopRankerArrays.size();
    }
    static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name,txt_marks,txt_rank;
        RelativeLayout layout_rank;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name=itemView.findViewById(R.id.txt_name);
            txt_marks=itemView.findViewById(R.id.txt_marks);
            txt_rank=itemView.findViewById(R.id.txt_rank);

        }
    }
}

