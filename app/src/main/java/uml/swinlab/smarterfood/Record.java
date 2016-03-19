package uml.swinlab.smarterfood;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kinse on 3/18/2016.
 */
public class Record {

    public static String filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "SmartFood";
    public static String filename = filePath + File.separator + "FoodLog.txt";
    public Record() {

    }

    public static void writeToFile(LogData data) {
        File file = new File(filePath);
        if(!file.exists())
            file.mkdirs();

        file = new File(filename);
        if(!file.exists()){
            Log.e("FileWrite", "File creation is failed");
        }

        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file, true);
            String msg = data.foodInfo + "\n" + data.startTime + "\n" + data.endTime + "\n" + data.isConfirmed + "\n";
            outputStream.write(msg.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return;
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<LogData> readFromFile(){
        ArrayList<LogData> log = new ArrayList<LogData>();

        return log;
    }
}
