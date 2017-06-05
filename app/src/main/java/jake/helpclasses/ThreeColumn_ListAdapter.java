package jake.helpclasses;
/**
 * Created by Mitch on 2016-05-13.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jake.R;
import jake.helpclasses.User;

/**
 * Created by Mitch on 2016-05-06.
 */
public class ThreeColumn_ListAdapter extends ArrayAdapter<User> {

    private LayoutInflater mInflater;
    private ArrayList<User> users;
    private int mViewResourceId;

    public ThreeColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<User> users) {
        super(context, textViewResourceId, users);
        this.users = users;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        User user = users.get(position);
        TextView rank = (TextView) convertView.findViewById(R.id.rank);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView points = (TextView) convertView.findViewById(R.id.points);

        if(position == 0){

            rank.setText("Rank");
            //rank.setGravity(View.TEXT_ALIGNMENT_CENTER);
            name.setText("Name");
            points.setText("Points");

            if (user != null) {
                if (rank != null) {
                    rank.setText(user.getUserRank());
                }
                if (name != null) {
                    name.setText((user.getUserName()));
                }
                if (points != null) {
                    points.setText(user.getUserPoints());
                }
            }
            return convertView;
        }

        else{
            if (user != null) {
                if (rank != null) {
                    rank.setText(user.getUserRank());
                }
                if (name != null) {
                    name.setText((user.getUserName()));
                }
                if (points != null) {
                    points.setText(user.getUserPoints());
                }
            }
            return convertView;
        }


    }
}