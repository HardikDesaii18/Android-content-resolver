package com.example.hardikdesaii.contentresolverdemo;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.UserViewHolder> {
    Context context;
    ArrayList<SmsData> datalist;
    SmsAdapter(Context context,ArrayList<SmsData> datalist)
    {
        this.context=context;
        this.datalist=datalist;
    }
    @Override
    public SmsAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_sms, null);
        SmsAdapter.UserViewHolder userViewHolder = new SmsAdapter.UserViewHolder(view);
        return userViewHolder;

    }

    @Override
    public void onBindViewHolder(SmsAdapter.UserViewHolder holder, int position) {

        SmsData data=datalist.get(position);
        String date=getDate(Long.parseLong(data.getDate()));
         final String message=data.getMessage();

        holder.name.setText(data.getSender());
        if(data.getMessage().length()>25)
        {
            holder.message.setText(data.getMessage().substring(0,25)+"....");
        }
        else
        {
            holder.message.setText(data.getMessage());
        }

        holder.date.setText(date);
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+message,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name,date,message;
        CardView cardview;
        public UserViewHolder(View itemView)
        {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.fragementimage);
            name=(TextView)itemView.findViewById(R.id.fragementname);
            message=(TextView)itemView.findViewById(R.id.fragementmessage);
            date=(TextView)itemView.findViewById(R.id.fragementdate);
            cardview=(CardView)itemView.findViewById(R.id.fragementcardview);
        }
    }
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}
