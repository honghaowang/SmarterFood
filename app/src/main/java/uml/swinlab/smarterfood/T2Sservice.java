package uml.swinlab.smarterfood;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by kinse on 3/18/2016.
 */
public class T2Sservice implements TextToSpeech.OnInitListener{
    private TextToSpeech tts;
    public static boolean initState = false;
    private ArrayList<String> questionList = new ArrayList<String>();
    private String TAG = "SmartFood.T2S";

    public T2Sservice(Context context){
        tts = new TextToSpeech(context, this);
        questionList.add("Are you eating? Please answer yes or no.");
        questionList.add("What do you eat?");
    }
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.US);
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e(TAG, "this language is not supported");
            }
            else{
                initState = true;
            }
        }
        else
            Log.e(TAG, "Initialization is failed!");
    }

    public static boolean getInitState() { return initState; }

    public void speakOut(int i){
        tts.setPitch( 0.6f );
        tts.setSpeechRate( 0.6f );
        tts.speak(questionList.get(i), TextToSpeech.QUEUE_FLUSH, null);
    }
}
