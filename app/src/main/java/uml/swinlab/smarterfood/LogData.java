package uml.swinlab.smarterfood;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kinse on 3/18/2016.
 */
public class LogData {
    public static String startTime;
    public static  String endTime;
    public static  String foodInfo;
    public static  Boolean isConfirmed;


    public LogData(String start){
        startTime = start;
    }

    public static void setEndTime(String end){
        endTime = end;
    }

    public static void setFoodInfo(String food){
        foodInfo = food;
    }

    public static void setIsConfirmed(Boolean confirm){
        isConfirmed = confirm;
    }

    public static String formatTime(){
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return currentTime;
    }
}
