package jake.helpclasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jake on 2017-06-03.
 */

public class User {
    private int id;
    private String address, name, level, points, rank;

    public User(JSONObject info) {
        try {
            this.id = Integer.parseInt(info.getString("id"));
            this.address = info.getString("address");
            this.name = info.getString("name");
            this.level = info.getString("level");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User(String rank, String name, String points) {
        this.name = name;
        this.points = points;
        this.rank = rank;
    }

    public int getUserid() {
        return id;
    }

    public String getUserAddress() {
        return address;
    }

    public String getUserName() {
        return name;
    }

    public String getUserLevel() {
        return level;
    }

    public String getUserPoints() {
        return points;
    }

    public String getUserRank() {
        return rank;
    }

    @Override
    public String toString() {
        return name;
    }
}
