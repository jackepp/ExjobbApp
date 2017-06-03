package jake.helpclasses;

/**
 * Created by jake on 2017-05-30.
 */

public class Task {
    private int id;
    private String name;
    private int valuePerHouR;

    public Task(int id, String name, int vph){
        this.id = id;
        this.name = name;
        this.valuePerHouR = vph;
    }
    public int getTaskId(){
        return id;
    }
    public String getTaskName(){
        return name;
    }
    public int getValuePerHouR(){
        return valuePerHouR;
    }

    @Override
    public String toString(){
        return name;
    }
}
