package com.albaresapps.plugins.devicenotifications;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class MobileDeviceNotifications extends CordovaPlugin {

  //private variables
  private final String ACTIONINVOKEDEVICENOTIFICATION = "InvokeDeviceNotification";
  private String title = "";
  private String message = "";

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    Log.i("ANDROIDNOTIFICATIONPLUGIN", "Notification Plugin Launching");

    //local variables
    JSONObject jsonObject = new JSONObject();
    this.title = args.getString(0);
    this.message = args.getString(1);

    //check for device notification
    if(this.ACTIONINVOKEDEVICENOTIFICATION.equals(action)) {
      Log.i("ANDROIDNOTIFICATIONPLUGIN", "Notification Plugin invoked");
      this.showNotification();
      jsonObject.put("status", "success");
      callbackContext.success(jsonObject);
      return true;
    }
    else {
      jsonObject.put("status", "failure");
      callbackContext.error(jsonObject);
      return true;
    }

  }

  //this function will init a simple notification
  private void showNotification() {

      //init the sound uri for the notification sound
      Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
      Log.i("ANDROIDNOTIFICATIONPLUGIN", "Sound Url defined");

      //init the notification builder
      NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this.cordova.getActivity());
      mBuilder.setSmallIcon(android.R.drawable.ic_popup_reminder);
      mBuilder.setContentTitle(this.title);
      mBuilder.setContentText(this.message);
      mBuilder.setSound(soundUri);
      Log.i("ANDROIDNOTIFICATIONPLUGIN", "Notification builder defined");

      //init the parent activity intent
      Class<?> parentClass = this.cordova.getActivity().getParent().getClass();
      Intent parentIntent = new Intent(this.cordova.getActivity(), parentClass);

      //init backstack
      TaskStackBuilder stackBuilder = TaskStackBuilder.create(this.cordova.getActivity());
      stackBuilder.addParentStack(parentClass);
      stackBuilder.addNextIntent(parentIntent);
      PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
      mBuilder.setContentIntent(resultPendingIntent);
      Log.i("ANDROIDNOTIFICATIONPLUGIN", "Intent defined");

      //fire off the notification with the handler
      NotificationManager nManager = (NotificationManager)this.cordova.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
      nManager.notify(1, mBuilder.build());
      Log.i("ANDROIDNOTIFICATIONPLUGIN", "Notification posted");
  }

  /*//this function will check if the R class contains the sub-class raw
  private Boolean containsRawClass(String className) {
      try {
          Class <?> rClass = Class.forName(this.cordova.getActivity().getPackageName() + ".R");
          Class[] rawClasses = rClass.getClasses();
          for(Class c : rawClasses) {
              if(c.getSimpleName().equals(className)) {
                  return true;
              }
          }
          return false;
      }
      catch(Exception ex) {
          return false;
      }
  }*/
}
