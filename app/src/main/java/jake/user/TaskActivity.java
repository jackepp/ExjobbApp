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
import org.json.JSONObject;

import jake.loginregister.R;

public class TaskActivity extends AppCompatActivity {

    JSONObject jsonOject;
    JSONArray jsonarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Spinner taskList = (Spinner) findViewById(R.id.taskList);
        Spinner timeList = (Spinner) findViewById(R.id.timeList);
        final Button bConfirmTask = (Button) findViewById(R.id.bConfirmTask);


        bConfirmTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }


}