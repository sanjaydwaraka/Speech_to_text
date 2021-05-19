package com.example.speechtotext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.opengl.GLES11Ext;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class SpeakActivity extends AppCompatActivity {
    TextView textView;
    SpeechRecognizer msr;
    Intent msri;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;//permission

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        checkPermission();
        textView = findViewById(R.id.result);
        msr = SpeechRecognizer.createSpeechRecognizer(this);
        msri = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        msri.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        msri.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        msr.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null) {
                    textView.setText(matches.get(0));
                }

            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        findViewById(R.id.micbtn).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        msr.stopListening();
                        textView.setHint("Here is the Input!!");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        textView.setText("");
                        textView.setHint("Listening...");
                        msr.startListening(msri);
                        break;
                }
                return false;
            }
        });
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package" + getPackageName()));
                startActivity(intent);
                finish();
            }

        }
    }
}