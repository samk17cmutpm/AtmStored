package com.sam_nguyen.atmstored.ui.atms;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sam_nguyen.atmstored.R;
import com.sam_nguyen.atmstored.data.Atm;

import java.util.List;

/**
 * Created by samnguyen on 2/8/17.
 */

public class AtmsAdapter extends RecyclerView.Adapter<AtmsAdapter.ViewHolder> {
    private List<Atm> atms;

    private Context context;

    public AtmsAdapter(List<Atm> atms, Context context) {
        this.atms = atms;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View atmItem = inflater.inflate(R.layout.atm_item, parent, false);

        AtmsAdapter.ViewHolder viewHolder = new AtmsAdapter.ViewHolder(atmItem);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Atm atm = atms.get(position);

        TextView tvAtmName = (TextView) holder.tvAtmName;

        TextView tvAtmAddress = (TextView) holder.tvAtmAddress;

        tvAtmName.setText(atm.getName());
        tvAtmAddress.setText(atm.getAddress());

    }

    @Override
    public int getItemCount() {
        return atms.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    private static AtmsAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(AtmsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvAtmName;

        public TextView tvAtmAddress;

        public ViewHolder(final View itemView) {
            super(itemView);

            tvAtmName = (TextView) itemView.findViewById(R.id.tv_atm_name);

            tvAtmAddress = (TextView) itemView.findViewById(R.id.tv_atm_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }

    }
}
