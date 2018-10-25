package sems.activityrecognition.gui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;
import java.util.List;

import sems.activityrecognition.services.ActivityRecognitionService;
import sems.activityrecognition.services.ActivityTransitionService;

public class ActivityServiceHelper {

    private Context context;

    public ActivityServiceHelper(Context context) {
        this.context = context;
    }

    public PendingIntent getActivityRecognitionServicePendingIntent() {
        Intent intent = new Intent(context, ActivityRecognitionService.class);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public PendingIntent getActivityTransitionServicePendingIntent() {
        Intent intent = new Intent(context, ActivityTransitionService.class);
        return PendingIntent.getService(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public ActivityTransitionRequest buildTransitionRequest() {
        List<ActivityTransition> transitions = new ArrayList();
        Integer[] activities = new Integer[]{
                DetectedActivity.IN_VEHICLE,
                DetectedActivity.ON_BICYCLE,
                DetectedActivity.RUNNING,
                DetectedActivity.STILL,
                DetectedActivity.WALKING
        };
        Integer[] transitionTypes = new Integer[]{
                ActivityTransition.ACTIVITY_TRANSITION_ENTER,
                ActivityTransition.ACTIVITY_TRANSITION_EXIT
        };
        for (int activity : activities) {
            for (int transitionType : transitionTypes) {
                transitions.add(new ActivityTransition.Builder()
                        .setActivityType(activity)
                        .setActivityTransition(transitionType)
                        .build());
            }
        }
        return new ActivityTransitionRequest(transitions);
    }

}
