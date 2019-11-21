package appdev.alarmclockapp;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import computerorg.alarmclockapp.R;


@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Alarm>  alarmList;
    Context context;


    RecyclerViewAdapter(Context context, ArrayList<Alarm> alarmList) {
        Log.d(TAG, "RecyclerViewAdapter Constructor Called");
        this.alarmList = alarmList;
        this.context = context;
    }


    void addAndNotify(Context context, String name, String description, int repeat,
            Calendar calendar) {
        System.out.println(alarmList);

        Alarm alarm = new Alarm(context, name, description, calendar, repeat);
        alarmList.add(alarm);
        this.notifyItemInserted(alarmList.size() - 1);
    }


    void addAndNotify(Alarm alarm) {
        alarmList.add(alarm);
        this.notifyItemInserted(alarmList.size() - 1);
    }


    private void removeAndNotify(int position) {
        if (alarmList.size() == 1) {
            position = 0;
        }

        alarmList.get(position).cancelAlarm();
        alarmList.remove(position);
        this.notifyItemRemoved(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent,
                false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called");

        holder.alarmName.setText(alarmList.get(position).name);
        holder.dateOutput.setText(alarmList.get(position).calendar.getTime().toString());
        holder.alarmDesc.setText(alarmList.get(position).description);
        if (alarmList.get(position).repeatMinutes > 0) {
            holder.repeatOutput.setText("Repeats Every " + alarmList.get(position).repeatMinutes + " Minutes");
        }
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alarmList.size() != 0) {
                    removeAndNotify(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return alarmList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView alarmName;
        TextView alarmDesc;
        TextView dateOutput;
        TextView repeatOutput;
        ImageView deleteButton;
        RelativeLayout parentLayout;

        ViewHolder(View alarmView) {
            super(alarmView);

            alarmName = alarmView.findViewById(R.id.alarm_name);
            alarmDesc = alarmView.findViewById(R.id.alarm_desc);
            dateOutput = alarmView.findViewById(R.id.date_out);
            deleteButton = alarmView.findViewById(R.id.delete_button);
            parentLayout = alarmView.findViewById(R.id.parent_layout);
            repeatOutput = alarmView.findViewById(R.id.repeat_out);
        }
    }
}
