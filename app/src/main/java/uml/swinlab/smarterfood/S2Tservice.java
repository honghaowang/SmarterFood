package uml.swinlab.smarterfood;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
/**
 * Created by kinse on 3/18/2016.
 */
public class S2Tservice extends Service {
    protected AudioManager mAudioManager;
    public SpeechRecognizer mSpeechRecognizer;
    protected Intent mSpeechRecognizerIntent;

    @Override
    public void onCreate() {
        Log.d("S2T", "on Create");
        super.onCreate();
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        mSpeechRecognizer.setRecognitionListener(new SpeechRecognitionListener());
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 2);
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
    }

    @Override
    public void onDestroy() {
        Log.d("S2T", "onDestroy");
        super.onDestroy();
        mSpeechRecognizer.stopListening();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

class SpeechRecognitionListener implements RecognitionListener
{
    private String TAG = "Listener";
    private String text = " ";
    @Override
    public void onBeginningOfSpeech()
    {
        Log.d(TAG, "on Beginning of speech");
    }

    @Override
    public void onBufferReceived(byte[] buffer)
    {
        Log.d(TAG, "on Buffer Received");
    }

    @Override
    public void onEndOfSpeech()
    {
        Log.d(TAG, "on end of speech");
        Log.e(TAG, text);
    }

    @Override
    public void onError(int error)
    {
        Log.d(TAG, "on Error");
        String message;
        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }

        Log.e(TAG, message);
    }

    @Override
    public void onEvent(int eventType, Bundle params)
    {
        Log.d(TAG, "on event");
    }

    @Override
    public void onPartialResults(Bundle partialResults)
    {
        Log.d(TAG, "on partial results");
    }

    @Override
    public void onReadyForSpeech(Bundle params)
    {
        Log.e(TAG, "on Ready for speech");

    }

    @Override
    public void onResults(Bundle results)
    {
        Log.d(TAG, "on Result");
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        text = matches.get(0);
        Log.e(TAG, text);
    }

    @Override
    public void onRmsChanged(float rmsdB)
    {
        Log.d(TAG, "on Rms changed");
    }

}