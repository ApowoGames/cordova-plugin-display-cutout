package com.apowo.tq.cutout;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Build;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.DisplayCutout;
import android.view.WindowManager;
import android.view.Window;
import android.view.WindowInsets;

public class CDVDisplayCutout extends CordovaPlugin {
    private CallbackContext context;
	private Activity activity;
	private Window window;
	

    @Override
    public boolean execute (String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        context = callbackContext;
        activity = this.cordova.getActivity();
        window = activity.getWindow();

        if("setDisplayCutout".equals(action)){
            return setDisplayCutout(args,callbackContext);
        }else if("getDisplayCutout".equals(action)){
            return getDisplayCutout(args,callbackContext);
        }

         // Action not found
        callbackContext.error("action not recognised");
        return false;
    }

    private boolean setDisplayCutout(JSONArray args, CallbackContext callbackContext){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.P) return false;

        int mode = args.optInt(0);

       activity.runOnUiThread(new Runnable(){
           @Override
           public void run(){
            try{
                    WindowManager.LayoutParams attr = window.getAttributes();
                    attr.layoutInDisplayCutoutMode = mode;

                    window.setAttributes(attr);

                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
            }catch(Exception e){
                callbackContext.error(e.getMessage());
            }

           }
           
       });

        return true;
    }

    private boolean getDisplayCutout(JSONArray args, CallbackContext callbackContext){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, 0));
            return true;
        }
        Activity activity = cordova.getActivity();
        activity.runOnUiThread(new Runnable(){
            @Override
            public void run(){
                try{
                    final WindowInsets insets = getInsets();
                    final DisplayCutout cutout = insets.getDisplayCutout();

                    float dens = 1 / activity.getResources().getDisplayMetrics().density;
                    float bottom = cutout != null ? (cutout.getSafeInsetBottom() * dens) : 0; 
                    float left = cutout != null ? (cutout.getSafeInsetLeft() * dens) : 0; 
                    float right = cutout != null ? (cutout.getSafeInsetRight() * dens) : 0; 
                    float top = cutout != null ? (cutout.getSafeInsetTop() * dens) : 0; 
            
					JSONObject json = new JSONObject();
            		json.put("left", left);
            		json.put("top", top);
            		json.put("right", right);
            		json.put("bottom", bottom);

                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, json));
                }catch (Exception e){
                    callbackContext.error(e.getMessage());
                }
            }
        });
       
        return true;
    }

    @TargetApi(23)
    private WindowInsets getInsets() {
        return this.webView.getView().getRootWindowInsets();
    }
}