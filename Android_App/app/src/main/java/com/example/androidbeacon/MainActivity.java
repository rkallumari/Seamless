package com.example.androidbeacon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;
import org.w3c.dom.Document;

import java.util.Arrays;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;

public class MainActivity extends AppCompatActivity {

    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    private BLEUtils ble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ble = new BLEUtils(getApplicationContext());
        ble.startTransmitting();
        setContentView(R.layout.activity_main);
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 3:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.d("MESSAGE", "User granted permission for running in background");
                    startService(new Intent(this, RunBackground.class));
                }
                break;

            default:
                break;
        }
    }*/

    public void startTransmittingInBackground(View view) {
        //view.findViewById(R.id.backgroundYes).setVisibility(View.INVISIBLE);
        //view.findViewById(R.id.backgroundNo).setVisibility(View.VISIBLE);
        startService(new Intent(this, RunBackground.class));
    }

    public void stopTransmittingInBackground(View view) {
        //view.findViewById(R.id.backgroundYes).setVisibility(View.VISIBLE);
        //view.findViewById(R.id.backgroundNo).setVisibility(View.INVISIBLE);
        stopService(new Intent(this, RunBackground.class));
    }
}
