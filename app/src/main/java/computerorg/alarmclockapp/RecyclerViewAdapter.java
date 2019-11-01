package computerorg.alarmclockapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Alarm> alarmList = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<Alarm> alarmList) {
        this.context = context;
        this.alarmList = alarmList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent,
                false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called");

        holder.AlarmName.setText(alarmList.get(position).alarmName);
        holder.DateOutput.setText(alarmList.get(position).triggerDate.toString());
    }


    @Override
    public int getItemCount() {
        return alarmList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView AlarmName;
        TextView DateLabel;
        TextView DateOutput;
        TextView TimeLabel;
        TextView TimeOutput;

        public ViewHolder(View alarmView) {
            super(alarmView);

            AlarmName = alarmView.findViewById(R.id.parent_layout);
            DateLabel = alarmView.findViewById(R.id.date_label);
            DateOutput = alarmView.findViewById(R.id.date_out);
            TimeLabel = alarmView.findViewById(R.id.time_label);
            TimeOutput = alarmView.findViewById(R.id.time_out);
        }
    }
}
