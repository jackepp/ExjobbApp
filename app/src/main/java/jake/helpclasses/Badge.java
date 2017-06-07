package jake.helpclasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jake on 2017-06-05.
 */

public class Badge {
    private int id;
    private String name, desc;

    public Badge(JSONObject info) {
        try {
            this.id = info.getInt("id");
            this.name = info.getString("name");
            this.desc = info.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getBadgeId() {
        return id;
    }

    public String getBadgeDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return name;
    }


}
