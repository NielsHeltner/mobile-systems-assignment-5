package sems.activityrecognition.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;

import sems.activityrecognition.R;

public class ActivityRecognitionService extends IntentService {

    protected static final String TAG = "ActivityRecognitionService";

    public ActivityRecognitionService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

            Log.d("fuck", "result in recognition service: " + result.getProbableActivities().toString());

            Intent i = new Intent(getString(R.string.RECEIVER_RECOGNITION_TAG));
            i.putExtra(getString(R.string.RESULT_TAG), result);
            sendBroadcast(i);
        }
    }

}
