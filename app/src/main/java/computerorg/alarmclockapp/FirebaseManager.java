package computerorg.alarmclockapp;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

class FirebaseManager {
    private static final String TAG = "FIREBASE_MANAGER";
    private static final DatabaseReference DB = FirebaseDatabase.getInstance().getReference();


    static void addAlarm(Alarm alarm) {
        DatabaseReference childRef = DB.push();
        childRef.setValue(alarm);
        alarm.setDbReference(childRef.getKey());
        childRef.getKey();
    }


    static void removeAlarm(String databaseRef) {
        DB.child(databaseRef).removeValue();
    }


    static void getEntries(final RecyclerViewAdapter adapter) {

        DB.addValueEventListener(new ValueEventListener() {
            boolean init = false;

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                if (!init) {
                    Log.d(TAG, "Outputting Alarms");

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Alarm alarm = child.getValue(Alarm.class);
                        assert alarm != null;
                        alarm.setDbReference(child.getKey());
                        adapter.addAndNotify(alarm);
                    }

                    init = true;
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
