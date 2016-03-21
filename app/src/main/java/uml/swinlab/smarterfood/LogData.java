package uml.swinlab.smarterfood;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kinse on 3/18/2016.
 */
public class LogData {
    public static String startTime = null;
    public static  String endTime = null;
    public static  String foodInfo = null;
    public static  String isConfirmed = null;

    public LogData(String start){
        startTime = start;
    }

    public static void setStartTime(String start){
        startTime = start;
    }

    public static void setEndTime(String end){
        endTime = end;
    }

    public static void setFoodInfo(String food){
        foodInfo = food;
    }

    public static void setIsConfirmed(String confirm){
        isConfirmed = confirm;
    }

    public String getStartTime(){ return startTime;    }

    public String getEndTime(){ return endTime; }

    public String getFoodInfo(){ return foodInfo; }

    public String getIsConfirmed(){ return isConfirmed;}

    public static String formatTime(){
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return currentTime;
    }

    public static Boolean iscompleted(){
        if(startTime == null)
            return false;
        else if(endTime == null)
            return false;
        else if(foodInfo == null)
            return false;
        else if(isConfirmed == null)
            return false;
        else
            return true;
    }

    public static void empty(){
        startTime = null;
        endTime = null;
        foodInfo = null;
        isConfirmed = null;
    }
}
