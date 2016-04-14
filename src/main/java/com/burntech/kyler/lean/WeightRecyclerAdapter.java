package com.burntech.kyler.lean;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kyler on 5/14/2015.
 */
public class WeightRecyclerAdapter extends RecyclerView.Adapter<WeightRecyclerAdapter.ViewHolder> {

    private ArrayList<Weight> _weights;
    private OnItemClickListener _onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View _view;
        TextView _txtName;
        TextView _txtDate;

        public ViewHolder(View itemView) {
            super(itemView);
            _view = itemView;
            _txtName = (TextView) itemView.findViewById(R.id.item_weight);
            _txtDate = (TextView) itemView.findViewById(R.id.item_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (_onItemClickListener != null) {
                _onItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    public WeightRecyclerAdapter(ArrayList<Weight> weights) {
        _weights = weights;
    }

    @Override
    public WeightRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weight_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder._txtName.setText(_weights.get(position).getWeight());
        holder._txtDate.setText(_weights.get(position).getDatetime());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        _onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return _weights.size();
    }

}
