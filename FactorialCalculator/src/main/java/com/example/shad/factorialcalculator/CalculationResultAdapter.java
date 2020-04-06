package com.example.shad.factorialcalculator;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CalculationResultAdapter extends RecyclerView.Adapter<CalculationResultAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvId;
        final TextView tvResult;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.tvId = itemView.findViewById(android.R.id.text1);
            this.tvResult = itemView.findViewById(android.R.id.text2);
        }
    }

    private List<CalculationResult> mData;

    public CalculationResultAdapter(final List<CalculationResult> data) {
        mData = data;
    }

    public void setData(final List<CalculationResult> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CalculationResult calculationResult = mData.get(position);
        holder.tvId.setText(String.valueOf(calculationResult.id));
        holder.tvResult.setText(calculationResult.result);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
