package sems.activityrecognition.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

import sems.activityrecognition.R;

public class ActivityRecognitionService extends IntentService {

    protected static final String TAG = "ActivityRecognitionService";

    private List<DetectedActivity> probableActivities;

    public ActivityRecognitionService() {
        super(TAG);
        Log.d("fuck", "recog service started");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            probableActivities = result.getProbableActivities();

            Log.d("fuck", "result in recognition service: " + probableActivities.toString());

            Intent i = new Intent(getString(R.string.RECEIVER_RECOGNITION_TAG));
            i.putExtra(getString(R.string.RESULT_TAG), result);
            sendBroadcast(i);
        }
    }

}
