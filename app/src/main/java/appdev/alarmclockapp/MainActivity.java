package appdev.alarmclockapp;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.mortbay.jetty.Main;

import java.util.ArrayList;
import java.util.Calendar;
import computerorg.alarmclockapp.R;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";
    protected Calendar currentCalendar = Calendar.getInstance();
    private LocationManager locationManager;
    private MyLocationListener locationListener;
    private Location location;
    private RecyclerViewAdapter adapter;
    private TextView nameInput;
    private TextView descInput;
    private TextView repeatInput;
    private TextView latitude;
    private TextView longitude;
    private TextView locationTimeOut;
    private TextView locationAlarmLat;
    private TextView locationAlarmLong;
    private Location recordedLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started app");

        Log.d(TAG, "DIR: " + getApplicationInfo().dataDir);

        this.nameInput = findViewById(R.id.name_input);
        this.descInput = findViewById(R.id.description_input);
        this.longitude = findViewById(R.id.current_long_out);
        this.latitude = findViewById(R.id.current_lat_out);
        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ArrayList<Alarm> alarmList = new ArrayList<>();
        this.adapter = new RecyclerViewAdapter(this, alarmList);
        this.locationListener = new MyLocationListener();
        this.repeatInput = findViewById(R.id.repeat_input);
        this.locationTimeOut = findViewById(R.id.location_time_out);
        this.locationAlarmLat = findViewById(R.id.recorded_lat_out);
        this.locationAlarmLong = findViewById(R.id.recorded_long_out);

        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setAdapter(this.adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        11);
            }
        } else {
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100,
                    1, this.locationListener);
            if (this.locationManager != null) {
                this.location = this.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }


        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (location != null) {
                latitude.setText("Latitude: " + String.valueOf(location.getLatitude()));
                longitude.setText("Longitude: " + String.valueOf(location.getLongitude()));
                locationTimeOut.setText(currentCalendar.getTime().toString());
            }
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Goto Settings Page To Enable GPS", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(callGPSSettingIntent);
                        }
                    });
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }

        // -------------------------  Buttons -------------------------
        Button addAlarmBtn = findViewById(R.id.confirm_add_button);
        addAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean useLocation = false;
                int repeatVal = 0;
                CheckBox locationBox = findViewById(R.id.location_checkbox);
                if (locationBox.isChecked()) {
                    recordedLocation = location;
                    locationAlarmLat.setText(String.valueOf(recordedLocation.getLatitude()));
                    locationAlarmLong.setText(String.valueOf(recordedLocation.getLongitude()));
                    locationTimeOut.setText(currentCalendar.getTime().toString());
                    Intent intent = new Intent(MainActivity.this, AlertReceiver.class);
                    intent.putExtra("name", "Stand up!");
                    intent.putExtra("desc", "Time to move!");
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this.getApplicationContext(),
                            -1, intent, 0);
                    AlarmManager alarmManager = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentCalendar.getTimeInMillis(), pendingIntent);
                } else {
                    try {
                        if ((Integer.parseInt(repeatInput.getText().toString()) < 60 ||
                                Integer.parseInt(repeatInput.getText().toString()) > 0)) {
                            repeatVal = Integer.parseInt(repeatInput.getText().toString());
                        }
                    } catch (NumberFormatException e) {
                        repeatVal = 0;
                    } finally {
                        adapter.addAndNotify(MainActivity.this, nameInput.getText().toString(),
                                descInput.getText().toString(), repeatVal, currentCalendar);
                    }
                }

                nameInput.setText("");
                descInput.setText("");
            }
        });

        Button buttonTimePicker = findViewById(R.id.button_timepicker);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment(currentCalendar);
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Button buttonDatePicker = findViewById(R.id.button_datepicker);
        buttonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment(currentCalendar);
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    private class MyLocationListener implements LocationListener {
        private static final String TAG = "LOCATION";

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged");
            latitude.setText(String.valueOf(location.getLatitude()));
            longitude.setText(String.valueOf(location.getLongitude()));
            locationAlarmLat.setText("-");
            locationAlarmLong.setText("-");
            if (!locationTimeOut.getText().toString().equals("No Location Alarm Set")) {
                locationTimeOut.setText("No Location Alarm Set");
            }

            Intent intent = new Intent(MainActivity.this, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this.getApplicationContext(),
                    -1, intent, 0);

            AlarmManager alarmManager = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}