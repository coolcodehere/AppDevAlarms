package appdev.alarmclockapp;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import computerorg.alarmclockapp.R;

public class AlertReceiver extends BroadcastReceiver {
    private static final String TAG = "ALERT_RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        NotificationHelper notificationHelper = new NotificationHelper(context, intent);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
    }

    private class NotificationHelper extends ContextWrapper {
        public static final String channelID = "channelID";
        public static final String channelName = "Channel Name";

        private NotificationManager mManager;
        private Intent intent;

        public NotificationHelper(Context base, Intent intent) {
            super(base);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createChannel();
            }

            this.intent = intent;
        }

        @TargetApi(Build.VERSION_CODES.O)
        private void createChannel() {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

            getManager().createNotificationChannel(channel);
        }

        public NotificationManager getManager() {
            if (mManager == null) {
                mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }

            return mManager;
        }

        public NotificationCompat.Builder getChannelNotification() {
            return new NotificationCompat.Builder(getApplicationContext(), channelID)
                    .setContentTitle(this.intent.getStringExtra("name"))
                    .setContentText(this.intent.getStringExtra("desc"))
                    .setSmallIcon(R.drawable.ic_launcher_foreground);
        }
    }
}