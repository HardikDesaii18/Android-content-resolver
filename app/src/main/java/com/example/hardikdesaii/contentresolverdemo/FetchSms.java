package com.example.hardikdesaii.contentresolverdemo;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class FetchSms extends AppCompatActivity
{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<String> tabList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        tabList=getTabs();
        Log.e("Tab size",""+tabList.size());
        setContentView(R.layout.activity_fetch_sms);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // add fragements here
        adapter.addFragment(new FragementSmsInbox(), "INBOX");
        adapter.addFragment(new FragementSmsSent(), "SENT");
//        for(int i=0;i<tabList.size();i++)
//        {
//            if(i==0)
//            {
//                adapter.addFragment(new FragementSmsInbox(),""+tabList.get(i));
//            }
//            else if(i%2==0)
//            {
//                adapter.addFragment(new FragementSmsInbox(),""+tabList.get(i));
//            }
//            else
//            {
//                adapter.addFragment(new FragementSmsSent(),""+tabList.get(i));
//            }
//        }
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.home)
        {
            Intent intent=new Intent(FetchSms.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public ArrayList<String> getTabs()
    {
        ArrayList<String> list=new ArrayList<>();
        list.add("SHIRTS");
        list.add("BLAZER");
        list.add("3 PIECE");
        list.add("PANT");


        return list;
    }

}
