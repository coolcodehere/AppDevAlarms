package computerorg.alarmclockapp;

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
    private ArrayList<Alarm> alarmList;
    private Context context;


    public RecyclerViewAdapter(Context context, ArrayList<Alarm> alarmList) {
        Log.d(TAG, "RecyclerViewAdapter Constructor Called");
        this.context = context;
        this.alarmList = alarmList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent,
                false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called");

        holder.alarmName.setText(alarmList.get(position).name);
        holder.dateOutput.setText(alarmList.get(position).date + " " + alarmList.get(position).time);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Deleting alarm: " + alarmList.get(position).name);

                alarmList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(0, alarmList.size());
            }
        });
    }


    @Override
    public int getItemCount() {
        return alarmList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView alarmName;
        TextView dateOutput;
        ImageView deleteButton;
        RelativeLayout parentLayout;

        public ViewHolder(View alarmView) {
            super(alarmView);

            alarmName = alarmView.findViewById(R.id.alarm_name);
            dateOutput = alarmView.findViewById(R.id.date_out);
            deleteButton = alarmView.findViewById(R.id.delete_button);
            parentLayout = alarmView.findViewById(R.id.parent_layout);
        }
    }
}
