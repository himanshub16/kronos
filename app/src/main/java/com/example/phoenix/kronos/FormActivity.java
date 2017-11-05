package com.example.phoenix.kronos;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.net.Uri;
import android.database.Cursor;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.hypertrack.lib.HyperTrack;

import java.util.Date;
import java.util.List;

public class FormActivity extends AppCompatActivity {

    private static final int RESULT_PICK_CONTACT = 2000;

    int PLACE_PICKER_REQUEST=1;
    LatLng ll;
    String locationname="";
    String description="";
    String actiontype="";
    Date datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Button placepicker= findViewById(R.id.placepickerbtn);
        placepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(FormActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        FloatingActionButton addtask = (FloatingActionButton)findViewById(R.id.addtaskbtn);
        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(FormActivity.this,"Adding a task here",Toast.LENGTH_SHORT).show();
            }
        });

        ListView sms_info_view = findViewById(R.id.sms_info_view);
        Spinner task_type = findViewById(R.id.task_type);
        task_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("i", Integer.toString(i));

                if (i == 0) {
                    // send sms
//                    sms_info_view.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                TextView p= findViewById(R.id.place);
                p.setText(place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == RESULT_PICK_CONTACT) {
            contactPicked(data);
        }
    }

    public void pickContact(View v)
    {
        Log.v("mobile no", "tapped");

        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, RESULT_PICK_CONTACT);;
    }

    public void contactPicked(Intent data) {
        Uri contactData = data.getData();
        String cNumber;
        Cursor c = managedQuery(contactData, null, null, null, null);
        if (c.moveToFirst()) {

            String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if (hasPhone.equalsIgnoreCase("1")) {
                Cursor phones = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                        null, null);
                phones.moveToFirst();
                cNumber = phones.getString(phones.getColumnIndex("data1"));
                ((EditText) findViewById(R.id.mobileNoEdit)).setText(cNumber);
                Log.v("number is:", cNumber);
            }
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Log.v("name is", name);
        }
    }
}
