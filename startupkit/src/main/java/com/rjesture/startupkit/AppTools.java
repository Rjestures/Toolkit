package com.rjesture.startupkit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppTools {
    public static final String appError = "Something went wrong";
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    public static Toast mToast;
    static ProgressDialog progressDialog;
    static Dialog dialog;
    private static boolean doubleBackToExitPressedOnce;

    public static void setLog(String title, String message) {
        try {
            Log.v(title, message);
        } catch (Exception e) {
            handleCatch(e);
        }
    }

    public static void printMessage(String message) {
        System.out.println("Message :  " + message);
    }

    public static void setLog(String title, String message, Throwable throwable) {
        try {
            Log.v(title, message, throwable);
        } catch (Exception e) {
            handleCatch(e);
        }
    }

    public static AlertDialog showAlertDialog(Context context, String title, String message, String positiveLabel,
                                              DialogInterface.OnClickListener positiveClick, String negativeLabel,
                                              DialogInterface.OnClickListener negativeClick, boolean isCancelable) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(title);
        dialogBuilder.setCancelable(isCancelable);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(positiveLabel, positiveClick);
        dialogBuilder.setNegativeButton(negativeLabel, negativeClick);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    public static void showToast(Context context, String text) {
        if (mToast != null && mToast.getView().isShown()) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        mToast.show();
    }

    public static void handleCatch(Exception e, Context context) {
        e.printStackTrace();
        showToast(context, appError);
    }

    public static void setImgPicasso(String imgUrl, Context context, ImageView imgView) {
        if (imgUrl.contains("dl=0")) {
            imgUrl = imgUrl.replace("dl=0", "raw=1");
        }
        if (!imgUrl.isEmpty())
            PicassoTrustAll.getInstance(context)
                    .load(imgUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(imgView);
    }

    public static String setPrice(String price) {
        return "₹ " + price;
    }

    public static String setPriceTotal(String price) {
        return "₹ " + price + " /-";
    }

    public static String setPriceTotal(int price) {
        return "₹ " + price + " /-";
    }

    public static String updateQuantity(String oldQuantity, int newQuantity) {
        if ((oldQuantity.equalsIgnoreCase("0") || oldQuantity.equalsIgnoreCase("")) && newQuantity <= 0)
            return "0";
        return Integer.toString(Integer.parseInt(oldQuantity) + newQuantity);
    }

    public static String addDays(String oldDate, int days) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        try {
            Date date = formatter.parse(oldDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.add(Calendar.DAY_OF_MONTH, days);

            System.out.println("Cool" + formatter.format(calendar.getTime()));
            return formatter.format(calendar.getTime());
        } catch (ParseException e) {
            System.out.println("Wrong date");
        }
        return "";
    }


    public static void handleCatch(Exception e) {
        e.printStackTrace();
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            try {
                @SuppressLint("WrongConstant") InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
                View view = activity.getCurrentFocus();
                if (view != null) {
                    IBinder binder = view.getWindowToken();
                    if (binder != null) {
                        inputMethodManager.hideSoftInputFromWindow(binder, 0);
                    }
                }
            } catch (Exception e) {
                handleCatch(e);
            }
        }

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @SuppressLint("NewApi")
    public static void backToExit(Activity mActivity) {
        if (doubleBackToExitPressedOnce) {
            mActivity.finishAffinity();
            return;
        }

        doubleBackToExitPressedOnce = true;

        showToast(mActivity, "Press again to exit");
        //Toast.makeText(mActivity, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;

            }
        }, 1000);

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String getTextInputEditTextData(TextInputEditText textInputEditText) {
        return textInputEditText.getText().toString().trim();
    }

    public static String getTextInputEditTextData(EditText textInputEditText) {
        return textInputEditText.getText().toString().trim();
    }

    public static boolean isValidPhone(String pass) {
        return pass != null && pass.length() == 10;
    }

    public static void showRequestDialog(Activity activity) {

        if (!activity.isFinishing()) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(activity);

                progressDialog.setCancelable(false);
                progressDialog.setMessage(activity.getString(R.string.please_wait));
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                progressDialog.show();
            }
        }


    }

    public static void showRequestDialog(Activity activity, String message) {
        if (progressDialog == null) {
            //progressDialog = new ProgressDialog(activity, R.style.MyAlertDialogStyle);
            progressDialog = new ProgressDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(message);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();
        }
    }

    public static void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static String toCamelCaseSentence(String s) {
        if (s == null) {
            return "";
        }
        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String toCamelCaseWord : words) {
            sb.append(toCamelCaseWord(toCamelCaseWord));
        }
        return sb.toString().trim();
    }

    public static String toCamelCaseWord(String word) {
        if (word == null) {
            return "";
        }
        switch (word.length()) {
            case 0:
                return "";
            case 1:
                return word.toUpperCase(Locale.getDefault()) + " ";
            default:
                return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase(Locale.getDefault()) + " ";
        }
    }

    public static void showGifDialog(Activity activity, int gif_icon_id) {

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //...set cancelable false so that it's never get hidden
        dialog.setCancelable(false);
        //...that's the layout i told you will inflate later
        dialog.setContentView(R.layout.sample);

        //...initialize the imageView form infalted layout
        ImageView gifImageView = dialog.findViewById(R.id.custom_loading_imageView);
        Glide.with(activity).asGif().load(gif_icon_id).into(gifImageView);

        //...finaly show it
        dialog.show();
    }

    //..also create a method which will hide the dialog when some work is done
    public static void hideGifDialog() {
        dialog.dismiss();
    }
    //..we need the context else we can not create the dialog so get context in constructor

}
