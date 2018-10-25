package sems.activityrecognition.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.ActivityTransitionResult;

import sems.activityrecognition.R;

public class ActivityTransitionService extends IntentService {

    protected static final String TAG = "ActivityTransitionService";

    public ActivityTransitionService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityTransitionResult.hasResult(intent)) {
            ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);

            Log.d(getString(R.string.app_name), "Result in transition service: " + result.getTransitionEvents().toString());

            Intent i = new Intent(getString(R.string.RECEIVER_TRANSITION_TAG));
            i.putExtra(getString(R.string.RESULT_TAG), result);
            i.putExtra(getString(R.string.TIME_TAG), System.currentTimeMillis());
            sendBroadcast(i);
        }
    }

}
