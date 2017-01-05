package com.manufacton.CaptureLogcat;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.lang.Process;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class CaptureLogcat extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("capture")) {
            String message = args.getString(0);
            this.capture(message, callbackContext);
            return true;
        }
        return false;
    }

    private void capture(String message, CallbackContext callbackContext) {
        try {
          Log.d("MF:APP", "In capture");
          Process process = Runtime.getRuntime().exec("logcat -d");
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
          Log.d("MF:APP", "Have output");
          StringBuilder log = new StringBuilder();
          String line = "";
          while ((line = bufferedReader.readLine()) != null) {
            log.append(line);
          }
          Log.d("MF:APP", "Sending result back with " + log.toString().length() + " chars");
          callbackContext.success(log.toString());
        } 
        catch (IOException e) {
            callbackContext.error("Error while capturing the log");
        }
    }
}
