package com.example.hardikdesaii.contentresolverdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.UserViewHolder>
{
    ArrayList<ContactData> arrayList;
    Context context;
    ContactsAdapter(ArrayList<ContactData> arrayList,Context context)
    {
        this.arrayList=arrayList;
        this.context=context;
    }

    @Override
    public ContactsAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_cardview, null);
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.UserViewHolder holder, int position) {

        ContactData data=arrayList.get(position);
        holder.image.setImageResource(Integer.parseInt(data.getContactImage()));
        holder.name.setText(data.getContactName());
        holder.number.setText(data.getContactNumber());
        holder.email.setText(data.getContactEmail());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name,number,email;
        public UserViewHolder(View itemView)
        {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.cardviewimage);
            name=(TextView)itemView.findViewById(R.id.cardviewname);
            email=(TextView)itemView.findViewById(R.id.cardviewemail);
            number=(TextView)itemView.findViewById(R.id.cardviewnumber);
        }
    }
}
