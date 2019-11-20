package appdev.alarmclockapp;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import computerorg.alarmclockapp.R;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";
    protected Calendar currentCalendar = Calendar.getInstance();
    private RecyclerViewAdapter adapter;
    private TextView nameInput;
    private TextView descInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started app");

        this.nameInput = findViewById(R.id.name_input);
        this.descInput = findViewById(R.id.description_input);

        ArrayList<Alarm> alarmList = new ArrayList<>();
        this.adapter = new RecyclerViewAdapter(this, alarmList);

        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setAdapter(this.adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));


        // -------------------------  Buttons -------------------------
        Button addAlarmBtn = findViewById(R.id.confirm_add_button);
        addAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addAndNotify(nameInput.getText().toString(), descInput.getText().toString(),
                        currentCalendar);
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

}