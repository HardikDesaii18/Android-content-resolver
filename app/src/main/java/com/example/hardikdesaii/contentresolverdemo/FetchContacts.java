package com.example.hardikdesaii.contentresolverdemo;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class FetchContacts extends AppCompatActivity
{
    ArrayList<ContactData> contactList;
      ProgressDialog pd;
    RecyclerView   recyclerView;
    ContactsAdapter contactAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_contacts);
        contactList=new ArrayList<ContactData>();
        recyclerView = (RecyclerView) findViewById(R.id.fetchcontacts);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.createcontact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent addContactIntent = new Intent(Contacts.Intents.Insert.ACTION, Contacts.People.CONTENT_URI);
               // addContactIntent.putExtra(Contacts.Intents.Insert.NAME, "NAME"); // an example, there is other data available
                startActivity(addContactIntent);
            }
        });


       if (Build.VERSION.SDK_INT >= 23)
       {
            insertDummyContactWrapper();
            // Marshmallow+

      } else
       {
         // Pre-Marshmallow
            getRecords();
        }




    } // on create ends here

    public void getRecords()
    {
        AsyncTask task=new AsyncTask()
        {
            @Override
            protected void onPreExecute() {
                pd=new ProgressDialog(FetchContacts.this);
                pd.setTitle("Please Wait");
                pd.setMessage("Synchronizing Contacts");
                pd.show();
            }

            @Override
            protected Object doInBackground(Object[] params)
            {

                contactList=getallcontacts();

                return true;
            }
            @Override
            protected void onPostExecute(Object o)
            {
                if(pd != null && pd.isShowing())
                {
                    pd.dismiss();

                }
                recyclerView.setLayoutManager(new LinearLayoutManager(FetchContacts.this));

                contactAdapter = new ContactsAdapter(contactList, FetchContacts.this);

                recyclerView.setAdapter(contactAdapter);

            }
        };
        task.execute();
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
            Intent intent=new Intent(FetchContacts.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<ContactData> getallcontacts()
    {
        ArrayList<ContactData> list=new ArrayList<>();
        String cname=" ";
        String cphoneNumber=" ";
        String cid=" ";
        String cemail=" ";
        int image=R.mipmap.ic_launcher;

        // contacts fetching code starts here
        ContentResolver contentResolver = getContentResolver();
      //  String query1 = ContactsContract.Contacts.DISPLAY_NAME+ " LIKE 'F%' ";
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null,null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if (cursor.getCount() > 0)
        {
            while (cursor.moveToNext())
            {
                    cemail="No Email";
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0)
                {
                    cid = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                     cname = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{cid},
                            null);
                    if (phoneCursor.moveToNext())
                        {
                         cphoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }

                    phoneCursor.close();


                    Cursor emailCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{cid}, null);
                    while (emailCursor.moveToNext())
                    {
                         cemail = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        Log.e("email "," "+cemail);
                    }
                    emailCursor.close();
                }
                Log.e("name"," "+cname);
                Log.e("number"," "+cphoneNumber);
                Log.e("email "," "+cemail);
                if(cemail.equals("No Email"))
                {
                    list.add(new ContactData(String.valueOf(image),cname,cphoneNumber," "));
                }
                else
                {
                    list.add(new ContactData(String.valueOf(image),cname,cphoneNumber,cemail));
                }

            }

            cursor.close();

        }
         return list;
    }

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void insertDummyContactWrapper()
    {
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.READ_CONTACTS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CODE_ASK_PERMISSIONS);

            return;
        }
        getallcontacts();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    getallcontacts();

                } else {
                    // Permission Denied
                    Toast.makeText(FetchContacts.this, "READ_CONTACTS  Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

} // main class ends here
