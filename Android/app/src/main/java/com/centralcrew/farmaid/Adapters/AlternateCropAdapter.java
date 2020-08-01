package com.centralcrew.farmaid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.centralcrew.farmaid.R;

import java.util.ArrayList;

public class AlternateCropAdapter extends RecyclerView.Adapter<AlternateCropAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> dataSet;
    private RvListener rvListener;

    public interface RvListener{
        public void setCrop(String title);
    }

    public void setCrops(ArrayList<String> dataSet){
        this.dataSet = dataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleTv;
        ImageView imageView;

        public MyViewHolder(View view){
            super(view);
            titleTv = view.findViewById(R.id.title_tv);
            imageView = view.findViewById(R.id.image_view);
        }
    }

    public AlternateCropAdapter(Context context, ArrayList<String> dataSet, RvListener rvListener){
        this.dataSet = dataSet;
        this.context = context;
        this.rvListener = rvListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alternate_crop_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final String title = dataSet.get(position);

        holder.titleTv.setText(title);

        switch (title){
            case "Rice":
                holder.imageView.setImageDrawable(context.getDrawable(R.drawable.ic_rice));
                break;

            case "Wheat":
                holder.imageView.setImageDrawable(context.getDrawable(R.drawable.ic_wheat));
                break;

            case "Maize":
                holder.imageView.setImageDrawable(context.getDrawable(R.drawable.ic_maize));
                break;

            case "Sugarcane":
                holder.imageView.setImageDrawable(context.getDrawable(R.drawable.ic_sugar_cane));
                break;

            case "Soyabean":
                holder.imageView.setImageDrawable(context.getDrawable(R.drawable.ic_soybean));
                break;

            case "Cotton":
                holder.imageView.setImageDrawable(context.getDrawable(R.drawable.ic_cotton));
                break;

            case "Arhar":
                holder.imageView.setImageDrawable(context.getDrawable(R.drawable.ic_pulses));
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvListener.setCrop(title);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
