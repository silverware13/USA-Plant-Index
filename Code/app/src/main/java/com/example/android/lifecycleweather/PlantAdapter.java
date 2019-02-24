package com.example.android.lifecycleweather;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.lifecycleweather.utils.USDAUtils;

import java.util.ArrayList;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantItemViewHolder> {

    private ArrayList<USDAUtils.PlantItem> mPlantItems;
    private OnPlantItemClickListener mPlantItemClickListener;

    public interface OnPlantItemClickListener {
        void onPlantItemClick(USDAUtils.PlantItem plantItem);
    }

    public PlantAdapter(OnPlantItemClickListener clickListener) {
        mPlantItemClickListener = clickListener;
    }

    public void updatePlantItems(ArrayList<USDAUtils.PlantItem> plantItems) {
        mPlantItems = plantItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mPlantItems != null) {
            return mPlantItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public PlantItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.plant_list_item, parent, false);
        return new PlantItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlantItemViewHolder holder, int position) {
        holder.bind(mPlantItems.get(position));
    }

    class PlantItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mPlantSciTV;
        private TextView mPlantComTV;
        private ImageView mPlantPicIV;

        public PlantItemViewHolder(View itemView) {
            super(itemView);
            mPlantSciTV = itemView.findViewById(R.id.tv_plant_scientific);
            mPlantComTV = itemView.findViewById(R.id.tv_plant_common);
            mPlantPicIV = itemView.findViewById(R.id.iv_plant_pic);
            itemView.setOnClickListener(this);
        }

        // Binds plant item to a view holder.
        public void bind(USDAUtils.PlantItem plantItem) {
            String sciString = plantItem.Scientific_Name_x;
            String comString = plantItem.Common_Name;
            //String iconURL = USDAUtils.buildIconURL(plantItem.icon);
            mPlantSciTV.setText(sciString);
            mPlantComTV.setText(comString);
            //Glide.with(mPlantPicIV.getContext()).load(iconURL).into(mPlantPicIV);
        }

        @Override
        public void onClick(View v) {
            USDAUtils.PlantItem plantItem = mPlantItems.get(getAdapterPosition());
            mPlantItemClickListener.onPlantItemClick(plantItem);
        }
    }
}
