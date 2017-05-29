package jake.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jake.loginregister.R;

public class TaskActivity extends AppCompatActivity {

    public ArrayList<String> getTasks(String fileName){
        JSONArray jsonArray = null;
        ArrayList<String> cList = new ArrayList<String>();
        try{
            InputStream is = getResources().getAssets().open(fileName);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();
            String json=new String(data,"UTF-8");
            jsonArray=new JSONArray(json);
            if(jsonArray!=null){
                for(int i=0; i<jsonArray.length();i++){
                    cList.add(jsonArray.getJSONObject(i).getString("cname"));
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException je){
            je.printStackTrace();
        }
        return cList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Spinner taskList = (Spinner) findViewById(R.id.taskList);
        ArrayList<String> tasks = getTasks("tasks.json");
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.activity_task, tasks);
            taskList.setAdapter(adapter);
        Spinner timeList = (Spinner) findViewById(R.id.timeList);
        final Button bConfirmTask = (Button) findViewById(R.id.bConfirmTask);


        bConfirmTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }


}