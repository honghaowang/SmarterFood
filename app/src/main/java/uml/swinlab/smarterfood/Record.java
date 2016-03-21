package uml.swinlab.smarterfood;

import android.os.Environment;
import android.util.Log;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
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
            String msg = data.startTime + "\n" + data.endTime + "\n" + data.foodInfo + "\n" + data.isConfirmed + "\n";
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

        try {
            File file = new File(filename);
            FileInputStream fin = new FileInputStream(file);
            DataInputStream dio = new DataInputStream(fin);
            String strLine = dio.readLine();

            while(strLine != null) {
                //Log.d("ReadLine", strLine);
                LogData data = new LogData(strLine);
                //data.setStartTime(strLine);
                data.setEndTime(dio.readLine());
                data.setFoodInfo(dio.readLine());
                data.setIsConfirmed(dio.readLine());
                Log.e("Read from File", data.getStartTime() + " ::: " + data.getEndTime() + " ::: " + data.getFoodInfo() + " ::: " + data.getIsConfirmed());
                log.add(data);
                for(int i=0; i<log.size(); i++)
                    Log.d("Read from File", log.get(i).getStartTime() + " ::: " + log.get(i).getEndTime() + " ::: " + log.get(i).getFoodInfo() + " ::: " + log.get(i).getIsConfirmed());
                strLine = dio.readLine();

            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        for(int i=0; i<log.size(); i++)
            Log.e("Read from File", log.get(i).getStartTime() + " ::: " + log.get(i).getEndTime() + " ::: " + log.get(i).getFoodInfo() + " ::: " + log.get(i).getIsConfirmed());
        return log;
    }
}
