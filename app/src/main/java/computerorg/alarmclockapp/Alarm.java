package computerorg.alarmclockapp;

import android.app.AlarmManager;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Alarm {
    private final String TAG = "ALARM";
    public String name;
    public String date;
    public String time;

    public Alarm() {

    }

    // dateTime should be passed in in the format "DD/MM/YYYY-HH:MM:SS"
    public Alarm(String name, String date, String time) {
        Log.d(TAG, "constructor");

        this.name = name;
        this.date = date;
        this.time = time;
    }
}
