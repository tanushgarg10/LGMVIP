package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    @NonNull
    private ArrayList<Model>stateList;
    private Context context;

    public Adapter(@NonNull ArrayList<Model> stateList, Context context) {
        this.stateList = stateList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.state_rv, parent, false);
        return new Adapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model statemodel= stateList.get(position);
        holder.Statename.setText(statemodel.getSname());
        holder.Distname.setText(statemodel.getDname());
        holder.Confirmed.setText(""+statemodel.getConfirmed());
        holder.Deceased.setText(""+statemodel.getDeceased());
        holder.Recovered.setText(""+statemodel.getRecovered());
        holder.Active.setText(""+statemodel.getActive());


    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public void filterList(ArrayList<Model> filteredlist) {
        stateList=filteredlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView  Statename;
        private  TextView  Distname;
        private TextView  Active;
        private TextView  Recovered;
        private TextView  Deceased;
        private TextView  Confirmed;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            Distname=itemView.findViewById(R.id.districtId);
            Active= itemView.findViewById(R.id.Activecases);
            Recovered= itemView.findViewById(R.id.Recoveredcases);
            Deceased= itemView.findViewById(R.id.Deathcases);
            Confirmed= itemView.findViewById(R.id.Confirmedcases);
            Statename= itemView.findViewById(R.id.StateId);



        }


    }
}
