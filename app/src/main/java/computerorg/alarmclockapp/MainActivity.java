package computerorg.alarmclockapp;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final int CREATE_ALARM = 1;

    private final String TAG = "MainActivity";
    private ArrayList<Alarm> alarmList = new ArrayList<>();
    private RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, alarmList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started app");

        FloatingActionButton addAlarmButton = findViewById(R.id.add_alarm);
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        CreateAlarmActivity.class);
                startActivityForResult(intent, CREATE_ALARM);
            }
        });

        initSavedAlarms();
        initRecyclerView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_ALARM) {
            ArrayList<String> list = data.getStringArrayListExtra("Data");
            Log.d(TAG, list.get(1) + ", " + list.get(2));

            Alarm alarm = new Alarm(list.get(0), list.get(1), list.get(2));
            FirebaseManager.addAlarm(alarm);
            alarmList.add(alarm);
            adapter.notifyItemRangeChanged(0, alarmList.size());
        } else {
            return;
        }
    }

    private void initSavedAlarms() {
        this.alarmList = FirebaseManager.getEntries();
        adapter.notifyItemRangeChanged(0, this.alarmList.size());
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: Loading recycler view");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecyclerViewAdapter(this, alarmList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}