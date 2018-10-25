package sems.activityrecognition.gui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sems.activityrecognition.R;
import sems.activityrecognition.gui.result_receivers.ActivityRecognitionResultReceiver;
import sems.activityrecognition.gui.result_receivers.ActivityTransitionResultReceiver;

public class MainActivity extends AppCompatActivity {

    public static final int DUTY_CYCLE_INTERVAL_MS = 5000;

    private Button sampleBtn;
    private TextView lastSampleRecogTime;
    private TextView lastSampleRecogValue;
    private TextView lastSampleTransTime;
    private TextView lastSampleTransValue;

    private ActivityServiceHelper activityServiceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lookUpViews();

        ActivityRecognitionResultReceiver activityRecognitionResultReceiver = new ActivityRecognitionResultReceiver(this);
        ActivityTransitionResultReceiver activityTransitionResultReceiver = new ActivityTransitionResultReceiver(this);
        activityRecognitionResultReceiver.registerReceiver();
        activityTransitionResultReceiver.registerReceiver();

        activityServiceHelper = new ActivityServiceHelper(this);
    }

    public void onSampleButtonClick(View view) {
        ActivityRecognitionClient activityRecognitionClient = ActivityRecognition.getClient(this);
        if (sampleBtn.getText().equals(getString(R.string.sample_btn_start))) {
            Task task = activityRecognitionClient.requestActivityUpdates(DUTY_CYCLE_INTERVAL_MS, activityServiceHelper.getActivityRecognitionServicePendingIntent());
            task.addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(getApplicationContext(), R.string.toast_sample_start_success, Toast.LENGTH_SHORT).show();
                    sampleBtn.setText(getString(R.string.sample_btn_stop));
                }
            });
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), R.string.toast_sample_start_failure, Toast.LENGTH_LONG).show();
                }
            });

            Task task1 = activityRecognitionClient.requestActivityTransitionUpdates(activityServiceHelper.buildTransitionRequest(), activityServiceHelper.getActivityTransitionServicePendingIntent());
            task1.addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(getApplicationContext(), R.string.toast_sample_start_success, Toast.LENGTH_SHORT).show();
                }
            });
            task1.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), R.string.toast_sample_start_failure, Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            activityRecognitionClient.removeActivityUpdates(activityServiceHelper.getActivityRecognitionServicePendingIntent());
            activityRecognitionClient.removeActivityTransitionUpdates(activityServiceHelper.getActivityTransitionServicePendingIntent());
            Toast.makeText(getApplicationContext(), R.string.toast_sample_stop, Toast.LENGTH_LONG).show();
            sampleBtn.setText(getString(R.string.sample_btn_start));
        }
    }

    public void onActivityRecognitionResult(ActivityRecognitionResult result) {
        DatabaseReference activityRecognitionRef = FirebaseDatabase.getInstance().getReference().child("activityRecognitions");

        activityRecognitionRef.child(String.valueOf(result.getTime())).setValue(result.getProbableActivities());

        lastSampleRecogTime.setText(getString(R.string.sample_recog_time, getTimeFormatted(result.getTime())));
        lastSampleRecogValue.setText(getString(R.string.sample_recog_value, result.getMostProbableActivity()));
    }

    public void onActivityTransitionResult(ActivityTransitionResult result, long timestamp) {
        DatabaseReference activityTransitionRef = FirebaseDatabase.getInstance().getReference().child("activityTransitions");

        activityTransitionRef.child(String.valueOf(timestamp)).setValue(result.getTransitionEvents());

        lastSampleTransTime.setText(getString(R.string.sample_trans_time, getTimeFormatted(timestamp)));
        lastSampleTransValue.setText(getString(R.string.sample_recog_value, result.getTransitionEvents()));
    }

    private String getTimeFormatted(long timeMs) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        return simpleDateFormat.format(new Date(timeMs));
    }

    private void lookUpViews() {
        sampleBtn = findViewById(R.id.sampleBtn);
        lastSampleRecogTime = findViewById(R.id.lastSampleRecogTime);
        lastSampleRecogValue = findViewById(R.id.lastSampleRecogValue);
        lastSampleTransTime = findViewById(R.id.lastSampleTransTime);
        lastSampleTransValue = findViewById(R.id.lastSampleTransValue);
    }

}
