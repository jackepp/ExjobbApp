package jake.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import jake.loginregister.R;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Spinner taskList = (Spinner) findViewById(R.id.taskList);
        Spinner timeList = (Spinner) findViewById(R.id.timeList);
        final Button bConfirmTask = (Button) findViewById(R.id.bConfirmTask);

        ArrayAdapter<String> taskAdapter = new ArrayAdapter<String>(TaskActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.tasks));
        taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskList.setAdapter(taskAdapter);

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(TaskActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.times));
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeList.setAdapter(timeAdapter);


        bConfirmTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PRINT", "bConfirmTask.onClick()");
            }
        });


    }


}