package com.example.phoenix.kronos;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hypertrack.lib.HyperTrack;
import com.hypertrack.lib.callbacks.HyperTrackCallback;
import com.hypertrack.lib.models.ErrorResponse;
import com.hypertrack.lib.models.SuccessResponse;
import com.hypertrack.lib.models.User;
import com.hypertrack.lib.models.UserParams;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        HyperTrack.initialize(MainActivity.this, "pk_test_1665645b7ddebd22fce62d7d0b1d693910daec76");
        checkForLocationSettings();
        UserParams userParams = new UserParams().setName("Nikhil")
                .setPhone("+91-7523979176")
                .setLookupId("+91-7523979176");

// This API will create a new user only if none exists already for the given lookup_id
        HyperTrack.getOrCreateUser(userParams, new HyperTrackCallback() {
            @Override
            public void onSuccess(@NonNull SuccessResponse response) {
                if (response.getResponseObject() != null) {
                    User user = (User) response.getResponseObject();
                    // Handle user_id, if needed
                    String userId = user.getId();
                    Log.v("anything",userId);
                    Toast.makeText(MainActivity.this, "User created successfully with UserId: " + userId, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(@NonNull ErrorResponse errorResponse) {
                // Handle createUser error here
                Toast.makeText(MainActivity.this, errorResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkForLocationSettings() {
        // Check for Location permission
        if (!HyperTrack.checkLocationPermission(this)) {
            HyperTrack.requestPermissions(this);
            return;
        }

        // Check for Location settings
        if (!HyperTrack.checkLocationServices(this)) {
            HyperTrack.requestLocationServices(this, null);
        }

        // Location Permissions and Settings have been enabled
        // Proceed with your app logic here
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == HyperTrack.REQUEST_CODE_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkForLocationSettings();

            } else {
                // Handle Location Permission denied error
                Toast.makeText(this, "Location Permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HyperTrack.REQUEST_CODE_LOCATION_SERVICES) {
            if (resultCode == Activity.RESULT_OK) {
                checkForLocationSettings();
            } else {
                // Handle Enable Location Services request denied error
                Toast.makeText(this, "Enable Location Services request denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
