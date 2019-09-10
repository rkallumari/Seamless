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
import android.widget.TextView;

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
        TextView textView =  findViewById(R.id.deviceUUID);
        textView.setText("Your UUID is "+ble.getUUID());
    }

    public void startTransmittingInBackground(View view) {
        startService(new Intent(this, RunBackground.class));
    }

    public void stopTransmittingInBackground(View view) {
        stopService(new Intent(this, RunBackground.class));
    }
}
