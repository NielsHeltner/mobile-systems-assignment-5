package sems.activityrecognition.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import sems.activityrecognition.R;

public class MainActivity extends AppCompatActivity {

    public static final int DUTY_CYCLE_INTERVAL = 3;

    private Button sampleBtn;
    private TextView totalSamplesTxt;
    private TextView lastSampleTime;
    private TextView lastSampleValues;

    private ScheduledExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lookUpViews();

        executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void onSampleButtonClick(View view) {
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
            }
        }, 0, DUTY_CYCLE_INTERVAL, TimeUnit.SECONDS);
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
