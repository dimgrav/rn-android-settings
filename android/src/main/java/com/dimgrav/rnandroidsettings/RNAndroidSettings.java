package com.dimgrav.rnandroidsettings;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;


import javax.annotation.Nonnull;


public class RNAndroidSettings extends ReactContextBaseJavaModule {

    private ReactContext reactContext;

    private static final Settings SETTINGS = new Settings();

    private static final String START_ACTIVITY_ERROR = "START_ACTIVITY_ERROR";
    private static final String DECLARED_FIELD_ERROR = "DECLARED_FIELD_ERROR";
    private static final String RESOLVE_ACTIVITY_ERROR = "RESOLVE_ACTIVITY_ERROR";

    public RNAndroidSettings(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Nonnull
    @Override
    public String getName() {
        return "RNAndroidSettings";
    }

    @ReactMethod
    public void launchSettings(String setting, Promise promise) {
        String value;
        try {
            value = Settings.class.getDeclaredField(setting).get(SETTINGS).toString();
        } catch (Exception e) {
            promise.reject(DECLARED_FIELD_ERROR, e);
            return;
        }
        String packageName = reactContext.getPackageName();

        Intent intent = new Intent(value);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        if (setting.equals(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)) {
            intent.setData(Uri.parse("package:" + packageName));
        }

        if (setting.equals(Settings.ACTION_APP_NOTIFICATION_SETTINGS)) {
            if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <= 25) {
                intent.putExtra("app_package", packageName);
                intent.putExtra("app_uid", reactContext.getApplicationInfo().uid);
            }
            if (Build.VERSION.SDK_INT >= 26) {
                intent.putExtra("android.provider.extra.APP_PACKAGE", packageName);
            }
        }

        if (intent.resolveActivity(reactContext.getPackageManager()) != null) {
            try {
                reactContext.startActivity(intent);
            } catch (Exception e) {
                promise.reject(START_ACTIVITY_ERROR, e);
            }

            WritableMap map = Arguments.createMap();
            map.putString("setting", setting);
            promise.resolve(map);
        }

        promise.reject(RESOLVE_ACTIVITY_ERROR, "Failed to resolve Activity for intent " + setting);
    }
}