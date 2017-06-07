package jake.helpclasses;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jake.activities.R;

public class TaskListAdapter extends ArrayAdapter<Task> {

    private LayoutInflater mInflater;
    private ArrayList<Task> tasks;
    private int mViewResourceId;

    public TaskListAdapter(Context context, int textViewResourceId, ArrayList<Task> tasks) {
        super(context, textViewResourceId, tasks);
        this.tasks = tasks;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        Task task = tasks.get(position);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView time = (TextView) convertView.findViewById(R.id.time);

        if (task != null) {
        }
        if (name != null) {
            name.setText(task.toString());
        }
        if (time != null) {
            time.setText(task.getValuePerHouR());
        }

        return convertView;
    }
}
