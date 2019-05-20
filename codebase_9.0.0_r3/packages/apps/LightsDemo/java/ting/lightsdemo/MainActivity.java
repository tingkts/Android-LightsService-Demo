package ting.lightsdemo;

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

public class MainActivity extends Activity {
    private final static String LOGTAG = MainActivity.class.getName();

    private Button buttonOn;
    private Button buttonOff;

    private EditText editTextColor;

    private int color;

    private IPowerManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pm = IPowerManager.Stub.asInterface(ServiceManager.getService(Context.POWER_SERVICE));

        buttonOn = findViewById(R.id.button);
        buttonOff = findViewById(R.id.button2);

        editTextColor = findViewById(R.id.editText);

        editTextColor.setText("0x"+Integer.toHexString(Color.RED));

        buttonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    color = Integer.parseInt(editTextColor.getText().toString());
                    pm.setAttentionLight(true, /*Color.RED*/color);
                } catch (RemoteException e) {
                }
            }
        });


        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    color = Integer.parseInt(editTextColor.getText().toString());
                    pm.setAttentionLight(false, /*Color.RED*/color);
                } catch (RemoteException e) {
                }
            }
        });

        // TODO: why runtime cann't find class def. of LightsManager.clasee in dex search path?
        //LightsManager lightsManager = LocalServices.getService(LightsManager.class);
        //Log.d(LOGTAG, "onCreate: lightsManager=" + lightsManager);
    }
}