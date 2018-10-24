package sems.activityrecognition.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

import sems.activityrecognition.R;

public class ActivityTransitionService extends IntentService {

    protected static final String TAG = "ActivityTransitionService";

    private List<ActivityTransitionEvent> activityTransitions;

    public ActivityTransitionService() {
        super(TAG);
        Log.d("fuck", "trantzi service started");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityTransitionResult.hasResult(intent)) {
            ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);
            activityTransitions = result.getTransitionEvents();

            Log.d("fuck", "result in transition service: " + activityTransitions.toString());

            Intent i = new Intent(getString(R.string.RECEIVER_TRANSITION_TAG));
            i.putExtra(getString(R.string.RESULT_TAG), result);
            sendBroadcast(i);
        }
    }

}
