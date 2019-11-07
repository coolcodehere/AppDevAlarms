package computerorg.alarmclockapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class CreateAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_create);

        Button confirm = findViewById(R.id.confirm_alarm_btn);
        confirm.setOnClickListener(new View.OnClickListener() {
            // We need to validate date/time input here
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                Intent returnIntent = new Intent();
                returnIntent.putStringArrayListExtra("Data", list);
                TextView name = findViewById(R.id.name_input);
                TextView date = findViewById(R.id.date_input);
                TextView time = findViewById(R.id.time_input);

                list.add(name.getText().toString());
                list.add(date.getText().toString());
                list.add(time.getText().toString());
                setResult(1, returnIntent);
                finish();
            }
        });

    }
}
