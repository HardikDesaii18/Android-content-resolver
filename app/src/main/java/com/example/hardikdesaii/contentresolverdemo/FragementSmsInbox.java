package com.example.hardikdesaii.contentresolverdemo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class FragementSmsInbox extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<SmsData> datalist;
    RecyclerView recyclerView;

    // TODO: Rename and change types of parameters


    public FragementSmsInbox() {
        // Required empty public constructor
    }


    public static FragementSmsInbox newInstance(String param1, String param2) {
        FragementSmsInbox fragment = new FragementSmsInbox();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datalist=new ArrayList<SmsData>();
        datalist=getmessage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragement_sms_inbox, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.inboxfragementrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SmsAdapter adapter = new SmsAdapter(getActivity(),datalist);

        recyclerView.setAdapter(adapter);
        return v;

    }


public ArrayList<SmsData> getmessage()
{
    String name=" ",message=" ";
    String date;
    ArrayList<SmsData> list=new ArrayList<>();


    Uri uri = Uri.parse("content://sms/inbox");
    Cursor c= getActivity().getContentResolver().query(uri, null, null ,null,null);


    // Read the sms data and store it in the list
    if(c.moveToFirst()) {
        for(int i=0; i < c.getCount(); i++) {
             message=(c.getString(c.getColumnIndexOrThrow("body")).toString());
            name=(c.getString(c.getColumnIndexOrThrow("address")).toString());
             date=(c.getString(c.getColumnIndexOrThrow("date")));
            Log.e("message"," "+message);
            Log.e("name"," "+name);
            Log.e("date"," "+date);
            Log.e("id"," "+i);
            list.add(new SmsData(i,name,message,date));
            c.moveToNext();
        }
    }
    c.close();

    return list;
}


}
