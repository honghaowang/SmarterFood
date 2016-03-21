package uml.swinlab.smarterfood;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Created by kinse on 3/18/2016.
 */
public class S2Tservice extends Service {

    protected AudioManager mAudioManager;
    public SpeechRecognizer mSpeechRecognizer;
    protected Intent mSpeechRecognizerIntent;
    private String text = " ";
    private T2Sservice speech;

    private BroadcastReceiver receiverAtT2S = new BroadcastReceiver() {

        private String msg;
        Intent msgIntent = new Intent();

        @Override
        public void onReceive(Context context, Intent intent) {
            LogData data = null;
            if(intent.getAction().equals("Audio_Detection_Result")){
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    if(extras.containsKey("isEating")){
                        msg = extras.getString("isEating");
                    }
                }
                if(msg.equals("Start")){
                    Log.e("Start", "---------->");
                    data = new LogData(LogData.formatTime());
                    Log.e("LogData", data.startTime);
                }
                else if(msg.equals("End")){
                    Log.e("End", "--------------->");
                    data.setEndTime(LogData.formatTime());
                    Log.e("LogData", LogData.startTime + ":::::" + LogData.endTime);
                    speech.speakOut(0);

                    msgIntent.setAction("startRecording");
                    sendBroadcast(msgIntent);
                }
            }
            if(intent.getAction().equals("startRecording")){
                Log.d("Receiver", "Get start Recording");
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);

            }
            if(intent.getAction().equals("stopRecording")){
                Log.d("Receiver", "Get stop recording");

                Log.e("Data", text);

                if(text.equals("no")){
                    data.setFoodInfo(" ");
                    data.setIsConfirmed(text);
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                }
                else if(text.equals("yes")){
                    Log.e("Stoprecording", "yes");
                    data.setIsConfirmed(text);
                    speech.speakOut(1);

                    msgIntent.setAction("startRecording");
                    sendBroadcast(msgIntent);
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                }
                else {
                    data.setFoodInfo(text);
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                }


                if(data.iscompleted()) {
                    Record.writeToFile(data);
                    Log.e("Data", data.getStartTime() + " ::: " + data.getEndTime() + " ::: " + data.getFoodInfo() + " ;;; " + data.getIsConfirmed());
                    data.empty();
                    Toast.makeText(context, "Finish", Toast.LENGTH_LONG).show();
                }

            }
        }
    };

    public void setText(String temp){
        text = temp;
    }

    //override for Service
    @Override
    public void onCreate() {
        super.onCreate();
        speech = new T2Sservice(this);

        //Initial Speech to Text
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        mSpeechRecognizer.setRecognitionListener(new SpeechRecognitionListener());
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        //
        //mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        //
        IntentFilter filter = new IntentFilter();
        filter.addAction("Audio_Detection_Result");
        filter.addAction("startRecording");
        filter.addAction("stopRecording");
        registerReceiver(receiverAtT2S,filter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiverAtT2S);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //function for S2T
    class SpeechRecognitionListener implements RecognitionListener
    {
        private String TAG = "Listener";
        public String tempText;
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
            try {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (!matches.isEmpty()) {
                    tempText = matches.get(0);
                    Log.e(TAG, tempText);
                    setText(tempText);

                    Intent i = new Intent();
                    i.setAction("stopRecording");
                    sendBroadcast(i);
                }
            } catch(NullPointerException e){
                e.printStackTrace();
            }

        }

        @Override
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "on Rms changed");
        }

    }


}

