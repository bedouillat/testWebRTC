package com.example.testwebrtc;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Permission utility functions
 */
public class PermissionUtil {

    public static final Set<String> sAllPermissionsList = new HashSet<>(Arrays.asList(
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.RECORD_AUDIO));

    public static boolean isAllPermissionsGranted(Context context) {
        for (String permission : sAllPermissionsList) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    public static Set<String> getNotGrantedPermissions(Context context) {
        Set<String> permissionsToAsk = new HashSet<>();
        for (String permission : sAllPermissionsList) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(context, permission)) {
                permissionsToAsk.add(permission);
            }
        }
        return permissionsToAsk;
    }

    public static void askPermissions(Activity activity, Set<String> permissionsSet, int permissionsCode) {
        ActivityCompat.requestPermissions(activity,
                permissionsSet.toArray(new String[permissionsSet.size()]), permissionsCode);
    }

}
