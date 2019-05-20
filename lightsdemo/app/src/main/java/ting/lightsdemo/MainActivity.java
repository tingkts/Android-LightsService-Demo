package ting.lightsdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.server.lights.Light;
import com.android.server.lights.LightsManager;
import com.android.server.LocalServices;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private final static String LOGTAG = MainActivity.class.getName();

    @BindView(R.id.spinner2) Spinner lightIdSpinner;
    @BindView(R.id.button) Button pulseButton;

    private LightsManager lightsManager;
    private Light light;

    private final static Integer[] LIGHT_IDs = new Integer[]{
            LightsManager.LIGHT_ID_BACKLIGHT,
            LightsManager.LIGHT_ID_KEYBOARD,
            LightsManager.LIGHT_ID_BUTTONS,
            LightsManager.LIGHT_ID_BATTERY,
            LightsManager.LIGHT_ID_NOTIFICATIONS,
            LightsManager.LIGHT_ID_ATTENTION,
            LightsManager.LIGHT_ID_BLUETOOTH,
            LightsManager.LIGHT_ID_WIFI
    };
    private final static String[] LIGHT_NAMEs = new String[]{
            "LIGHT_ID_BACKLIGHT",
            "LIGHT_ID_KEYBOARD",
            "LIGHT_ID_BUTTONS",
            "LIGHT_ID_BATTERY",
            "LIGHT_ID_NOTIFICATIONS",
            "LIGHT_ID_ATTENTION",
            "LIGHT_ID_BLUETOOTH",
            "LIGHT_ID_WIFI"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ButterKnife.bind(this);

        //PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        //pm.goToSleep(SystemClock.uptimeMillis());

        lightsManager = LocalServices.getService(LightsManager.class);

        ArrayAdapter<String> lightIds = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, LIGHT_NAMEs);
        lightIdSpinner.setAdapter(lightIds);
        lightIdSpinner.setSelection(4);
        lightIdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "您選擇了:" + LIGHT_NAMEs[position], Toast.LENGTH_SHORT).show();
                light = lightsManager.getLight(LIGHT_IDs[position]);
                Log.d(LOGTAG, "lightIdSpinner onItemSelected: " + light);
                pulseButton.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        pulseButton.setEnabled(false);
        pulseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOGTAG, "pulseButton onClick, " + light + " pulse");
                light.pulse();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
