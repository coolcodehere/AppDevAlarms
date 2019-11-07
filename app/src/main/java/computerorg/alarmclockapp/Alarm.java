package computerorg.alarmclockapp;

import android.app.AlarmManager;
import android.util.Log;

import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

public class Alarm {
    private final String TAG = "ALARM";
    public String name;
    public int type;
    public Calendar calendar = Calendar.getInstance();
    private AlarmManager manager;

    // dateTime should be passed in in the format "DD\MM\YYYY-HH:MM:SS"
    public Alarm(String name, String dateTime) {
        Log.d(TAG, "constructor called: " + dateTime);

        // This is broken right now, just make sure date and time is correct when it gets sent here.
        Pattern p = Pattern.compile("^[0-9][0-9]\\/[0-9][0-9]\\/[0-9][0-9][0-9][0-9]-[0-9][0-9]:" +
                "[0-9][0-9]:[0-9][0-9]$");
//        if (!p.matcher(dateTime).matches()) {
//            throw new InputMismatchException();
//        }

        this.name = name;
        String date = dateTime.split("-")[0];
        String time = dateTime.split("-")[1];

        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.split("/")[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(date.split("/")[1]));
        calendar.set(Calendar.YEAR, Integer.parseInt(date.split("/")[2]));
        calendar.set(Calendar.HOUR, Integer.parseInt(time.split(":")[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[0]));
        calendar.set(Calendar.SECOND, Integer.parseInt(time.split(":")[0]));
    }
}
