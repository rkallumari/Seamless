package com.example.androidbeacon;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

import java.util.Arrays;
import java.util.UUID;

public class BLEUtils {

    private static BeaconTransmitter beaconTransmitter;
    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    private Context applicationContext;
    private String nameSpace;
    private String instance;
    private final Beacon beacon;

    public BLEUtils(Context context) {
        this.applicationContext = context;
        this.nameSpace = createNameSpace();
        this.instance = createInstance();
        this.beacon = new Beacon.Builder()
                .setId1(nameSpace)
                .setId2(instance)
                .setManufacturer(0x0118)
                .setTxPower(-59)
                .setDataFields(Arrays.asList(new Long[] {0l}))
                .build();
    }

    public void startTransmitting() {
        if(beaconTransmitter == null) {
            beaconTransmitter = initialiseBeaconTransmitter();
        }
        beaconTransmitter.startAdvertising(beacon,new AdvertiseCallback() {
            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                Log.d("Message","Transmitting successfully!!");
                Log.d("MESSAGE",""+settingsInEffect.toString());
            }
            @Override
            public void onStartFailure(int errorCode) {
                super.onStartFailure(errorCode);
            }
        });
    }

    public void stopTransmitting() {
        beaconTransmitter.stopAdvertising();
    }

    private BeaconTransmitter initialiseBeaconTransmitter() {
        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19");
        BeaconTransmitter beaconTransmitter = new BeaconTransmitter(applicationContext, beaconParser);
        beaconTransmitter.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER);
        beaconTransmitter.setConnectable(true);
        beaconTransmitter.setBeacon(beacon);
        beaconTransmitter.setAdvertiseMode(1);
        return beaconTransmitter;
    }

    private synchronized static String generateUUID(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }

    private String createNameSpace() {
        String uuid = generateUUID(applicationContext);
        String uuidStart = uuid.substring(0,uuid.lastIndexOf("-"));
        String instance = uuid.substring(uuid.lastIndexOf("-")+1,uuid.length());
        String[] uuidStartArray = uuidStart.split("-");
        String namespace = "";
        for (String uuidS: uuidStartArray
        ) {
            namespace += uuidS;
        }
        return namespace;
    }

    private String createInstance() {
        String uuid = generateUUID(applicationContext);
        return uuid.substring(uuid.lastIndexOf("-")+1,uuid.length());
    }

}
