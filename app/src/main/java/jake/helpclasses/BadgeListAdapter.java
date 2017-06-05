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

import jake.activities.R;


/**
 * Created by Mitch on 2016-05-06.
 */
public class BadgeListAdapter extends ArrayAdapter<Badge> {

    private LayoutInflater mInflater;
    private ArrayList<Badge> badges;
    private int mViewResourceId;

    public BadgeListAdapter(Context context, int textViewResourceId, ArrayList<Badge> badges) {
        super(context, textViewResourceId, badges);
        this.badges = badges;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        Badge badge = badges.get(position);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView desc = (TextView) convertView.findViewById(R.id.desc);

            if (badge != null) {
                }
                if (name != null) {
                    name.setText(badge.toString());
                }
                if (desc != null) {
                    desc.setText(badge.getBadgeDesc());
                }

        return convertView;
            }
    }
