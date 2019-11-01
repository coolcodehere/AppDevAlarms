package computerorg.alarmclockapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import computerorg.alarmclockapp.ui.main.SectionsPagerAdapter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private ArrayList<Alarm> alarmList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        TabLayout.Tab alarmTab = tabs.getTabAt(0);
        TabLayout.Tab clockTab = tabs.getTabAt(1);
        alarmTab.setText("Alarms");
        clockTab.setText("No Clock Connected");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void loadAlarms() {
        Log.d(TAG, "Loading Alarms");
        // Call loading logic here
    }

    private void initRecyclerView() {
        Log.d(TAG, "Init RecyclerView");
        RecyclerView recyclerView = findViewById(R.id.recy)
    }

}