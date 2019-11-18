package computerorg.alarmclockapp;

import android.icu.text.Edits;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FirebaseManager {
    private static final String TAG = "FIREBASE_MANAGER";
    private static final DatabaseReference DB = FirebaseDatabase.getInstance().getReference();

    public static void addAlarm(Alarm alarm) {
        Log.d(TAG, "addAlarm: Called");
        HashMap<String, Alarm> alarmHashMap = new HashMap<>();

        alarmHashMap.put(alarm.name, alarm);
        DB.setValue(alarmHashMap);
    }

    public static ArrayList<Alarm> getEntries() {
        final ArrayList<Alarm> out = new ArrayList<>();

        DB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Outputting Alarms");
                Iterator iter = dataSnapshot.getChildren().iterator();
                System.out.println(dataSnapshot.getValue().toString());

                while (iter.hasNext()) {
                    DataSnapshot current = (DataSnapshot) iter.next();
                    Alarm alarm = current.getValue(Alarm.class);
                    out.add(alarm);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return out;
    }
}
