package uml.swinlab.smarterfood;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;
import android.speech.tts.UtteranceProgressListener;

/**
 * Created by kinse on 3/18/2016.
 */
public class T2Sservice implements TextToSpeech.OnInitListener{
    public TextToSpeech tts;
    public static boolean initState = false;
    private ArrayList<String> questionList = new ArrayList<String>();
    private String TAG = "T2Sservice";
    public T2Sservice(Context context){
        questionList.add("Are you eating? Please answer yes or no.");
        questionList.add("What do you eat?");
        //Initial Text to Speech
        tts = new TextToSpeech(context, this);
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                Log.e("T2S", "is Done------->");
            }

            @Override
            public void onError(String utteranceId) {

            }
        });

    }

    //override for T2S
    @Override
    public void onInit(int status) {
        Log.d(TAG, "init");
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

    public void speakOut(int i){
        tts.setPitch(0.6f);
        tts.setSpeechRate(0.95f);
        //tts.setVoice();
        tts.speak(questionList.get(i), TextToSpeech.QUEUE_FLUSH, null);

        while(!tts.isSpeaking());
        while(tts.isSpeaking());
    }
}

