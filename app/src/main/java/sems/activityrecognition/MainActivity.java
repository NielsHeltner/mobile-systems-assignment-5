package sems.activityrecognition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button sampleBtn;
    private TextView totalSamplesTxt;
    private TextView lastSampleTime;
    private TextView lastSampleValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lookUpViews();
    }

    public void onSampleButtonClick(View view) {

    }

    private void lookUpViews() {
        sampleBtn = findViewById(R.id.sampleBtn);
        totalSamplesTxt = findViewById(R.id.totalSamplesTxt);
        lastSampleTime = findViewById(R.id.lastSampleTime);
        lastSampleValues = findViewById(R.id.lastSampleValues);
    }

}
