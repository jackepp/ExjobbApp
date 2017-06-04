package jake.helpclasses;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import jake.R;

import java.util.ArrayList;

import static jake.R.layout.row_layout;

/**
 * Created by jake on 2017-06-03.
 */

public class TeamAdapter extends ArrayAdapter{
    public TeamAdapter(@NonNull Context context, ArrayList<User> team) {
        super(context, row_layout, team);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View teamView = inflater.inflate(R.layout.row_layout, parent, false);

        //String user = getItem(position.to);
        return null;
    }

}
