package jake.helpclasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jake on 2017-06-03.
 */

public class User {
    private int id;
    private String address;
    private String name;
    private String email;
    private String usertype;
    private String level;
    private String  points;
    private String rank;

    public User(JSONObject info){
        try {
            this.id = Integer.parseInt(info.getString("id"));
            this.address = info.getString("address");
            this.name = info.getString("name");
            this.email = info.getString("email");
            this.usertype = info.getString("usertype");
            this.level = info.getString("level");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User(String rank, String name, String points){
        this.name = name;
        this.points = points;
        this.rank = rank;
    }


    public int getUserid(){
        return id;
    }
    public String getUserAddress(){
        return address;
    }
    public String getUserName(){
        return name;
    }
    public String getUserEmail(){
        return email;
    }
    public String getUsertype(){
        return usertype;
    }public String getUserLevel(){
        return level;
    }
    public String getUserPoints(){
        return points;
    }
    public String getUserRank(){
        return rank;
    }
    @Override
    public String toString(){
        return name;
    }
}
