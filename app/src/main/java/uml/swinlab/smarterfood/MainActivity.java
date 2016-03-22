package uml.swinlab.smarterfood;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        Record logRecord = new Record();
        data = logRecord.readFromFile();
        for(int i=0; i<data.size(); i++) {
            Log.d("At Main", data.get(i).getStartTime() + " ::: " + data.get(i).getEndTime() + " ::: " + data.get(i).getFoodInfo() + " ::: " + data.get(i).getIsConfirmed());
        }
        logAdapter = new LogAdapter(this, data);
        log.setAdapter(logAdapter);

        Intent i = new Intent(this, S2Tservice.class);
        startService(i);

        Intent broadIntent = new Intent();
        broadIntent.setAction("Audio_Detection_Result");
        broadIntent.putExtra("isEating", "S");
        sendBroadcast(broadIntent);

        IntentFilter filter = new IntentFilter();
        filter.addAction("LOG_UPDATA");
        registerReceiver(receiverAtMain, filter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logAdapter.notifyDataSetChanged();
    }

    private BroadcastReceiver receiverAtMain = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("LOG_UPDATA")){
                logAdapter.notifyDataSetChanged();
            }
        }
    };


    //Just for test
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
