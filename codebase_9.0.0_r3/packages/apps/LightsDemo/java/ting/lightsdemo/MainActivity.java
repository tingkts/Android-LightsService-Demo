package ting.lightsdemo2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.server.lights.Light;
import com.android.server.lights.LightsManager;
import com.android.server.LocalServices;

import android.content.Context;
import android.os.IPowerManager;
import android.os.ServiceManager;

import android.graphics.Color;

import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends Activity {
    private final static String TAG = MainActivity.class.getName();

    private Button buttonOn;
    private Button buttonOff;

    private Spinner spinner;

    private int color = Color.RED;

    private IPowerManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pm = IPowerManager.Stub.asInterface(ServiceManager.getService(Context.POWER_SERVICE));

        buttonOn = findViewById(R.id.button);
        buttonOff = findViewById(R.id.button2);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> lunchList = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.color,
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(lunchList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: " + position);
                if (position == 0) {
                    Log.d(TAG, "onItemSelected: red");
                    color = Color.RED;
                } else if (position == 1) {
                    Log.d(TAG, "onItemSelected: green");
                    color = Color.GREEN;   
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "here");
            }
        } );

        buttonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d(TAG, "setAttentionLight: true, " + Color.valueOf(color));
                    pm.setAttentionLight(true, color);
                } catch (RemoteException e) {
                }
            }
        });


        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d(TAG, "setAttentionLight: false, " + Color.valueOf(color));
                    pm.setAttentionLight(false, color);
                } catch (RemoteException e) {
                }
            }
        });

        //LightsManager lightsManager = LocalServices.getService(LightsManager.class);
        //Log.d(LOGTAG, "onCreate: lightsManager=" + lightsManager);
    }
}