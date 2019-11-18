package computerorg.alarmclockapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Alarm>  alarmList;


    RecyclerViewAdapter(Context context, ArrayList<Alarm> alarmList) {
        Log.d(TAG, "RecyclerViewAdapter Constructor Called");
        this.alarmList = alarmList;
    }


    void addAndNotify(String name, String date, String time) {
        System.out.println(alarmList);

        Alarm alarm = new Alarm(name, date, time);
        alarmList.add(alarm);
        FirebaseManager.addAlarm(alarm);
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

        FirebaseManager.removeAlarm(alarmList.get(position).getDbReference());
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


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called");

        holder.alarmName.setText(alarmList.get(position).name);
        holder.dateOutput.setText(alarmList.get(position).date + " " + alarmList.get(position).time);

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
        TextView dateOutput;
        ImageView deleteButton;
        RelativeLayout parentLayout;

        ViewHolder(View alarmView) {
            super(alarmView);

            alarmName = alarmView.findViewById(R.id.alarm_name);
            dateOutput = alarmView.findViewById(R.id.date_out);
            deleteButton = alarmView.findViewById(R.id.delete_button);
            parentLayout = alarmView.findViewById(R.id.parent_layout);
        }
    }
}
