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
    private TextView totalSamplesTxt;
    private TextView lastSampleTime;
    private TextView lastSampleValues;

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
        Task task = activityRecognitionClient.requestActivityUpdates(DUTY_CYCLE_INTERVAL_MS, activityServiceHelper.getActivityRecognitionServicePendingIntent());
        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getApplicationContext(), R.string.toast_sample_start_success, Toast.LENGTH_SHORT).show();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), R.string.toast_sample_start_failure, Toast.LENGTH_LONG).show();
                Log.d("fuck", "NO recognition");
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
                Log.d("fuck", "NO transiziotn");
            }
        });
    }

    public void onActivityRecognitionResult(ActivityRecognitionResult result) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("activityRecognition");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        String t = simpleDateFormat.format(new Date(result.getTime()));

        Log.d("fuck", "time from result: " + t);

        Log.d("fuck", "time from current: " + getCurrentTimeFormatted());

        ref.setValue("Hello");
    }

    public void onActivityTransitionResult(ActivityTransitionResult result) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("activityTransition");

        //ref.child(result.get)

        ref.setValue("Hello");
    }

    private String getCurrentTimeFormatted() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        return simpleDateFormat.format(new Date());
    }

    private void lookUpViews() {
        sampleBtn = findViewById(R.id.sampleBtn);
        totalSamplesTxt = findViewById(R.id.totalSamplesTxt);
        lastSampleTime = findViewById(R.id.lastSampleTime);
        lastSampleValues = findViewById(R.id.lastSampleValues);
    }

}
