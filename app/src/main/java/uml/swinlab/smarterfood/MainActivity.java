package uml.swinlab.smarterfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        data = Record.readFromFile();
        logAdapter = new LogAdapter(this, data);
        log.setAdapter(logAdapter);

        Intent i = new Intent(this, S2Tservice.class);
        startService(i);

        Intent broadIntent = new Intent();
        broadIntent.setAction("Audio_Detection_Result");
        broadIntent.putExtra("isEating", "S");
        sendBroadcast(broadIntent);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logAdapter.notifyDataSetChanged();
    }


    public void yesClick(View view){
        Intent broadIntent = new Intent();
        broadIntent.setAction("Audio_Detection_Result");
        broadIntent.putExtra("isEating", "Start");
        sendBroadcast(broadIntent);
    }

    public void noClick(View view){
        Intent broadIntent = new Intent();
        broadIntent.setAction("Audio_Detection_Result");
        broadIntent.putExtra("isEating", "End");
        sendBroadcast(broadIntent);
    }
}
