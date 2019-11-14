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
    public Calendar calendar = Calendar.getInstance();
    private AlarmManager manager;
    public int type;

    // dateTime should be passed in in the format "DD/MM/YYYY-HH:MM:SS"
    public Alarm(String name, String dateTime) {
        Log.d(TAG, "constructor called");

        // TODO: Move this to where the date and time are parsed and send date here so an error message
        //   can be shown in the app.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date date = formatter.parse(dateTime);
            assert date != null;
            calendar.setTime(date);
        } catch (ParseException e) {
            Log.d(TAG, "Bad Date: " + dateTime);
        }
        this.name = name;


    }
}
