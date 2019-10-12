package com.mehedi.user.ubarparkingapps.PostWrite_Read;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.mehedi.user.ubarparkingapps.R;

import java.util.List;

/**
 * Created by User on 6/18/2019.
 */

public class PostReadAdaptar extends RecyclerView.Adapter<PostReadAdaptar.PostreadViewHolder>{
    private Context context;
    private List<Pojoclass> responses ;


    public PostReadAdaptar(Context context, List<Pojoclass> responses) {
        this.context = context;
        this.responses = responses;
    }

    @Override
    public PostreadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.search_single_row,parent,false);
        return new PostreadViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostreadViewHolder holder, int position) {
        holder.cartypeAndSlot.setText("Type: "+responses.get(position).getSloatType()+" , "+responses.get(position).getSloat()+" Slot Available");

        if(position % 7 == 0){
           // holder.cartypeAndSlot.setBackgroundColor(ContextCompat.getColor(context,R.color.green));
            //holder.cartypeAndSlot.setTextColor(ContextCompat.getColor(context,R.color.green));
            holder.Location.setTextColor(ContextCompat.getColor(context,R.color.purple));
        }else if(position % 7 == 1){
            //holder.cartypeAndSlot.setBackgroundColor(ContextCompat.getColor(context,R.color.blue));
           // holder.cartypeAndSlot.setTextColor(ContextCompat.getColor(context,R.color.blue));
            holder.Location.setTextColor(ContextCompat.getColor(context,R.color.purple));
        }else if(position % 7 == 2){
            //holder.cartypeAndSlot.setBackgroundColor(ContextCompat.getColor(context,R.color.purple));
           // holder.cartypeAndSlot.setTextColor(ContextCompat.getColor(context,R.color.purple));
            holder.Location.setTextColor(ContextCompat.getColor(context,R.color.orange));
        }else if(position % 7 == 3){
           // holder.cartypeAndSlot.setBackgroundColor(ContextCompat.getColor(context,R.color.orange));
           // holder.cartypeAndSlot.setTextColor(ContextCompat.getColor(context,R.color.orange));
            holder.Location.setTextColor(ContextCompat.getColor(context,R.color.purple));
        }else if(position % 7 == 4){
           // holder.cartypeAndSlot.setBackgroundColor(ContextCompat.getColor(context,R.color.darkblue));
           // holder.cartypeAndSlot.setTextColor(ContextCompat.getColor(context,R.color.darkblue));
            holder.Location.setTextColor(ContextCompat.getColor(context,R.color.purple));
        }else if(position % 7 == 5){
           // holder.cartypeAndSlot.setBackgroundColor(ContextCompat.getColor(context,R.color.darkpurple));
           // holder.cartypeAndSlot.setTextColor(ContextCompat.getColor(context,R.color.darkpurple));
            holder.Location.setTextColor(ContextCompat.getColor(context,R.color.blue));
        }else if(position % 7 == 6){
           // holder.cartypeAndSlot.setBackgroundColor(ContextCompat.getColor(context,R.color.darkgreen));
            //holder.cartypeAndSlot.setTextColor(ContextCompat.getColor(context,R.color.darkgreen));
            holder.Location.setTextColor(ContextCompat.getColor(context,R.color.green));
        }
        holder.Location.setText(responses.get(position).getLocation());
        holder.Address.setText(responses.get(position).getAddress());
        holder.perhourcost.setText("Per hour : "+String.valueOf(responses.get(position).getPerhourCost()));


    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    public  class PostreadViewHolder extends RecyclerView.ViewHolder{
        private TextView cartypeAndSlot;
        private TextView ownerName;
        private TextView Location;
        private TextView Address;
        private TextView HouseNo;
        private TextView perhourcost;
        private Boolean Booking;
        private Switch aSwitchForBoking;
        public PostreadViewHolder(View itemView) {
            super(itemView);

            cartypeAndSlot = itemView.findViewById(R.id.searchslottype);
            Location = itemView.findViewById(R.id.searchlocation);
            Address = itemView.findViewById(R.id.searchaddress);
            perhourcost = itemView.findViewById(R.id.searchperhourCost);
            cartypeAndSlot = itemView.findViewById(R.id.searchslottype);
        }
    }

}
