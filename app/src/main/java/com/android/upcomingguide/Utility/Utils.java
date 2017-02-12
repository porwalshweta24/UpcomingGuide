package com.android.upcomingguide.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static final boolean IS_DEBUG = true;
    public static  String BASE_URL = "https://guidebook.com/service/v2/";
    public static final int UPCOMING_GUIDE_TAG = 10001;

    public static void showLog(String tag, String value) {
        if (IS_DEBUG)
            Log.e(tag, value);
    }
    /*
    * getting screen width
    */
    public static int getScreenWidth(Context context) {
        int columnWidth;
        WindowManager wm = (WindowManager)context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }
    public static String showMonthDateFormatter(int monthOfYear) {
        SimpleDateFormat newformat = new SimpleDateFormat("MMM");
        SimpleDateFormat oldformat = new SimpleDateFormat("MM");

        String monthName = "";
        Date myDate;
        try {
            myDate = oldformat.parse(String.valueOf(monthOfYear));
            monthName = newformat.format(myDate);

        } catch (Exception e) {
            e.printStackTrace();
            monthName = "" + monthOfYear;

        }
        //Utils.showLog("MMM", monthName);
        return monthName;
    }
    public static String getDeviceId(Context context) {

        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
    public static void showAlertBox(Context context, String msg, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(context).setTitle(null).setMessage(msg).setPositiveButton("OK", okListener).show().
                setCancelable(false);
    }

    public static boolean isOnline(Context context) {

        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected();
    }

    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showAlertBoxForConfirmation(Context context, String msg, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {

        new AlertDialog.Builder(context).setTitle(null).setMessage(msg)
                .setPositiveButton("YES", okListener)
                .setNegativeButton("NO", cancelListener)
                .show().setCancelable(false);
    }

    public static void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    public static boolean isValidEmail(String emailId) {
        return Patterns.EMAIL_ADDRESS.matcher(emailId.trim()).matches();
    }

    public static boolean isValidMobile(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

    public static boolean isValidMonth(String month) {
        if (Integer.parseInt(month) > 0 && Integer.parseInt(month) <= 12)
            return true;
        else
            return false;
    }

    public static String upperStringConverter(String normalString) {
        return normalString.substring(0, 1).toUpperCase() + normalString.substring(1);
    }

    // get current time
    public static String getCurrentTime(Calendar calendar) {
        SimpleDateFormat df = new SimpleDateFormat("dd MMM HH:mm ");
        String formattedDate = df.format(calendar.getTime());
        return formattedDate;

    }



    public static String getCalculatedTime() {
        int hour, minute;
        Calendar now = Calendar.getInstance();
// AM/PM format
        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
        now.add(Calendar.MINUTE, 30);
        String dateFormat = df.format(now.getTime());
        String[] splitDateFormat = dateFormat.split(":");
        hour = Integer.parseInt(splitDateFormat[0]);
        if (splitDateFormat[1].toLowerCase().contains("."))
//			if (Build.VERSION.SDK_INT >= 23)
        {
            minute = Integer.parseInt(splitDateFormat[1].toUpperCase().replace(" P.M.", "").replace(" A.M.", ""));
            if (dateFormat.toUpperCase().contains("P.M.")) {
                if (hour == 12)
                    hour = 0;
                else
                    hour = hour + 12;
            }
        } else {
            minute = Integer.parseInt(splitDateFormat[1].toUpperCase().replace(" PM", "").replace(" AM", ""));
            if (dateFormat.toUpperCase().contains("PM")) {
                if (hour == 12)
                    hour = 0;
                else
                    hour = hour + 12;
            }

        }

        String output = String.format("%02d:%02d", hour, minute);

        return output;
    }

    public static void hideKeyboard(Activity activity) {
        try {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void showKeyboard(Activity activity) {
        try {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
