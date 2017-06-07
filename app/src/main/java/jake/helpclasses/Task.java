package jake.helpclasses;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jake on 2017-05-30.
 */

public class Task {
    private int id;
    private String name;
    private int valuePerHouR;

    public Task(JSONObject info){
            try {
                this.id = info.getInt("id");
                this.name = info.getString("name");
                this.valuePerHouR = info.getInt("valuePerHour");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    public int getTaskId(){
        return id;
    }
    public int getValuePerHouR(){
        return valuePerHouR;
    }

    @Override
    public String toString(){
        return name;
    }
}
