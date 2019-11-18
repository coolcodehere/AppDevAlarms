package computerorg.alarmclockapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private RecyclerViewAdapter adapter;
    private TextView nameInput;
    private TextView dateInput;
    private TextView timeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started app");

        this.nameInput = findViewById(R.id.name_input);
        this.dateInput = findViewById(R.id.date_input);
        this.timeInput = findViewById(R.id.time_input);

        ArrayList<Alarm> alarmList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(this, alarmList);
        FirebaseManager.getEntries(adapter);

        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Button addAlarmBtn = findViewById(R.id.confirm_add_button);
        addAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addAndNotify(nameInput.getText().toString(), dateInput.getText().toString(),
                        timeInput.getText().toString());
            }
        });
    }
}