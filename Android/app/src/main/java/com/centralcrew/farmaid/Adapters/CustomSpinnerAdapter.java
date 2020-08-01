package com.centralcrew.farmaid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.centralcrew.farmaid.R;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> dataSet;
    LayoutInflater inflater;

    public CustomSpinnerAdapter(Context context, ArrayList<String> dataSet){
        this.context = context;
        this.dataSet = dataSet;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int i) {
        return dataSet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.spinner_layout, null);
        TextView sport = view.findViewById(R.id.text1);
        sport.setText(dataSet.get(i));
        return view;
    }
}
