package appdev.alarmclockapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)

public class Alarm {
    private final String TAG = "ALARM";
    public String name;
    public String description;
    protected Calendar calendar;
    private int id = (int) System.currentTimeMillis();
    private int repeatMinutes = -1;
    AlarmManager alarmManager;
    Context context;


    Alarm(Context context, String name, String description, Calendar calendar) {
        Log.d(TAG, "constructor");
        this.context = context;
        this.alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        if (name.equals("")) {
            this.name = "Alarm";
        } else {
            this.name = name;
        }


        if (description.equals("")) {
            this.description = "No Description";
        } else {
            this.description = description;
        }

        this.calendar = calendar;
        startAlarm(calendar);
    }

    private void startAlarm(Calendar c) {
        Intent intent = new Intent(this.context, AlertReceiver.class);
        intent.putExtra("name", this.name);
        intent.putExtra("desc", this.description);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),
                this.id, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        System.out.println(c.getTimeInMillis());
        if (this.repeatMinutes < 0) {
            Log.d(TAG, "startAlarm: Timer");
            this.alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        } else {
            Log.d(TAG, "startAlarm: Repeating");
            this.alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                    1000 * 60 * this.repeatMinutes, pendingIntent);
        }

    }


    protected void cancelAlarm() {
        Intent intent = new Intent(this.context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),
                this.id, intent, 0);

        alarmManager.cancel(pendingIntent);
    }
}
