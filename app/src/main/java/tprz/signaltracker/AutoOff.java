package tprz.signaltracker;

import android.app.Notification;
import android.os.Handler;
import android.util.Log;
import android.widget.ToggleButton;

import java.util.logging.LogRecord;

/**
 * Created by tomprz on 26/05/2015.
 */
public class AutoOff {

    private static final String TAG = "AutoOff";
    private final ToggleButton button;
//    private final long AUTO_OFF_TIME = 5 * 1000;
    private final long AUTO_OFF_TIME = 60 * 60 * 1000;
    private final Runnable task;
    private final Handler handler;
    private boolean isEnabled;

    public AutoOff(final ToggleButton button) {
        this.handler = new Handler();
        this.button = button;
        this.task = new Runnable() {
            @Override
            public void run() {
                if(button.isChecked()) {
                    button.performClick();
                    Log.i(TAG, "Disabling Tracker after " + AUTO_OFF_TIME / 1000 + " seconds of running.");
                }
            }
        };
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        handler.removeCallbacks(task);
        if(isEnabled) {
            handler.postDelayed(task, AUTO_OFF_TIME);
        }
    }

    public void pipelineEnabled(boolean isTrackerEnabled){
        handler.removeCallbacks(task);
        if(isTrackerEnabled && isEnabled) {
            handler.postDelayed(task, AUTO_OFF_TIME);
        }
    }

}
