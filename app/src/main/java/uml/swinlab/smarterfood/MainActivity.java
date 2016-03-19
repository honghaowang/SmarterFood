package uml.swinlab.smarterfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = "FoodLog.Main";
    private ListView log;
    private ArrayList<LogData> data;
    private LogAdapter logAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log = (ListView) findViewById(R.id.Log);
        //data = new ArrayList<LogData>();
        data = Record.readFromFile();
        logAdapter = new LogAdapter(this, data);
        log.setAdapter(logAdapter);
    }


}
