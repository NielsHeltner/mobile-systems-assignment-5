package sems.activityrecognition.gui.result_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.ActivityTransitionResult;

import sems.activityrecognition.R;
import sems.activityrecognition.gui.MainActivity;

public class ActivityTransitionResultReceiver extends BroadcastReceiver {

    private MainActivity parent;
    private IntentFilter filter;

    public ActivityTransitionResultReceiver(MainActivity parent) {
        this.parent = parent;
        filter = new IntentFilter();
        filter.addAction(parent.getString(R.string.RECEIVER_TRANSITION_TAG));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("fuck", "received tranzit data");

        ActivityTransitionResult result = intent.getParcelableExtra(parent.getString(R.string.RESULT_TAG));
        parent.onActivityTransitionResult(result);
    }

    public void registerReceiver() {
        parent.registerReceiver(this, filter);
    }

}
